package com.ehayes.ppmtool.Service;

import com.ehayes.ppmtool.Exceptions.ProjectNotFoundException;
import com.ehayes.ppmtool.Repository.BacklogRepository;
import com.ehayes.ppmtool.Repository.ProjectRepository;
import com.ehayes.ppmtool.Repository.ProjectTaskRepository;
import com.ehayes.ppmtool.domain.Backlog;
import com.ehayes.ppmtool.domain.ProjectTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
     ProjectTaskRepository projectTaskRepository;

    @Autowired
     BacklogRepository backlogRepository;

    @Autowired
     ProjectRepository projectRepository;

    @Autowired
     ProjectService projectService;

    //create a method that returns a task
    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username){

        //all PTs to be added to a specific project so we have to find the back log first
        Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier,username).getBacklog(); //backlogRepository.findByProjectIdentifier(projectIdentifier);
        //set the backlog to PTs
        projectTask.setBacklog(backlog);
        //project sequence is going to be the identifier and the id of the task within the project
        Integer BacklogSequence = backlog.getPTSequence();
        //once we get the sequence increase it
        //Update backlog sequence
        BacklogSequence++;

        backlog.setPTSequence(BacklogSequence);
        //Add sequence to task
        projectTask.setProjectSequence(projectIdentifier+ "-" + BacklogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        //initial priority when priority is null
        if(projectTask.getPriority() == null || projectTask.getPriority() == 0){
            projectTask.setPriority(3);
        }
        //initial status when status is null. use enums?
        if(projectTask.getStatus() == "" || projectTask.getStatus() == null){
            projectTask.setStatus("To-Do");
        }

        return projectTaskRepository.save(projectTask);

    }

    public Iterable<ProjectTask>findBacklogById(String id, String username){
        projectService.findProjectByIdentifier(id, username);
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id, String username) {

        //make sure we are searching on an existing backlog
        projectService.findProjectByIdentifier(backlog_id, username);


        //make sure that our task exists
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);

        if (projectTask == null) {
            throw new ProjectNotFoundException("Project Task '" + pt_id + "' not found");
        }

        //make sure that the backlog/project id in the path corresponds to the right project
        if (!projectTask.getProjectIdentifier().equals(backlog_id)) {
            throw new ProjectNotFoundException("Project Task '" + pt_id + "' does not exist in project: '" + backlog_id);
        }

        return projectTask;
    }


    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id, String username){

         ProjectTask projectTask = findPTByProjectSequence(backlog_id,pt_id,username);
         projectTask = updatedTask;

        return projectTaskRepository.save(updatedTask);
    }

    public void deletePTByProjectSequence(String backlog_id, String pt_id, String username){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);

         projectTaskRepository.delete(projectTask);
    }
}
