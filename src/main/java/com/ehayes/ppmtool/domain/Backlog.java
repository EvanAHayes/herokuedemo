package com.ehayes.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Backlog {
    //Back log is being used to decouple the project task list from the project

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer PTSequence = 0;
    private String projectIdentifier;

    //OnetoOne with project
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH, orphanRemoval = true)
    @JoinColumn(name = "project_id", nullable = false)
    //if you have infinite recurson jason objects go to the child component and put JsonIgnore
    @JsonIgnore
    private Project project;

    //OneToMany Projecttask
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "backlog")
    private List<ProjectTask> projectTasks = new ArrayList<>();

    //use case for a project with different back logs

}
