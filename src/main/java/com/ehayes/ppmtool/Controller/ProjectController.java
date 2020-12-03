package com.ehayes.ppmtool.Controller;


import com.ehayes.ppmtool.Service.MapValidationErrorService;
import com.ehayes.ppmtool.Service.ProjectService;
import com.ehayes.ppmtool.domain.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;


@RestController
@CrossOrigin( origins = "http://localhost:3000")
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;
    @Autowired
    MapValidationErrorService mapValidationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result,
                                              Principal principal){
        //principal above is the principal comes from the security and comes from person logged in
        //owner of the token
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null){
            return errorMap;
        }
        //the get pricipal is used to set the relations to the project adn the use
        Project project1 = projectService.saveOrUpdateProject(project, principal.getName());
        return new ResponseEntity<>(project1, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects(Principal principal){
        return projectService.findAllProjects(principal.getName());
    }


    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId, Principal principal){
        Project project = projectService.findProjectByIdentifier(projectId, principal.getName());
        return new ResponseEntity<>(project, HttpStatus.OK);
    }


    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> delete(@PathVariable String projectId, Principal principal){
       projectService.deleteProject(projectId, principal.getName());
        return new ResponseEntity<>("Project with ID:" + projectId + "was deleted", HttpStatus.OK);
    }
}
