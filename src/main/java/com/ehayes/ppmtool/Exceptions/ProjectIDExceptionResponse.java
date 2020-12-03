package com.ehayes.ppmtool.Exceptions;

public class ProjectIDExceptionResponse {
    private String ProjectIdentifier;

    public ProjectIDExceptionResponse(String projectIdentifier) {
        ProjectIdentifier = projectIdentifier;
    }

    public String getProjectIdentifier() {
        return ProjectIdentifier;
    }

    public void setProjectIdentifier(String projectIdentifier) {
        ProjectIdentifier = projectIdentifier;
    }
}
