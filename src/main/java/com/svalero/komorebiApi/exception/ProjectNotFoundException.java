package com.svalero.komorebiApi.exception;

public class ProjectNotFoundException extends Exception {

    public ProjectNotFoundException() {
        super("Project does not exist");
    }

    public ProjectNotFoundException(String message) {
        super(message);
    }
}
