package com.ehayes.ppmtool.Service;

import com.ehayes.ppmtool.Exceptions.ProjectIdException;
import com.ehayes.ppmtool.Exceptions.ProjectNotFoundException;
import com.ehayes.ppmtool.Repository.BacklogRepository;
import com.ehayes.ppmtool.Repository.ProjectRepository;
import com.ehayes.ppmtool.Repository.UserRepository;
import com.ehayes.ppmtool.domain.Backlog;
import com.ehayes.ppmtool.domain.Project;
import com.ehayes.ppmtool.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;

    public Project saveOrUpdateProject(Project project, String username) {
        String UpperCaseProjectIdentifier = project.getProjectIdentifier().toUpperCase();

        if (project.getId() != null) {
            Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectLeader());
            if (existingProject != null && (!existingProject.getProjectLeader().equals(username))) {
                throw new ProjectNotFoundException("Project not in your account");
            } else if (existingProject == null) {
                throw new ProjectNotFoundException("Project with ID: " + project.getProjectIdentifier() + " cannot be updated because it does not exist");
            }
        }

            try {

                //first we have to find that user
                User user = userRepository.findByUsername(username);
                project.setUser(user);
                project.setProjectLeader(user.getUsername());
                project.setProjectIdentifier(UpperCaseProjectIdentifier);

                if (project.getId() == null) {
                    //This will create a new back long only when there is a new project
                    Backlog backlog = new Backlog();
                    project.setBacklog(backlog);
                    backlog.setProject(project);
                    backlog.setProjectIdentifier(UpperCaseProjectIdentifier);
                }

                if (project.getId() != null) {
                    project.setBacklog(backlogRepository.findByProjectIdentifier(UpperCaseProjectIdentifier));
                }

                return projectRepository.save(project);
            } catch (Exception ex) {
                throw new ProjectIdException("Project ID " + UpperCaseProjectIdentifier + " already exist");
            }

        }


        public Project findProjectByIdentifier (String projectId, String username){

            Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

            if (project == null) {
                throw new ProjectIdException("Project ID " + projectId + " Doesn't exist");
            }

            if (!project.getProjectLeader().equals(username)) {
                throw new ProjectNotFoundException("Project Not Found in your account ");
            }
            return project;
        }

        public Iterable<Project> findAllProjects (String username){

            return projectRepository.findAllByProjectLeader(username);
        }

        public void deleteProject (String projectId, String username){
            projectRepository.delete(findProjectByIdentifier(projectId, username));
        }


}
