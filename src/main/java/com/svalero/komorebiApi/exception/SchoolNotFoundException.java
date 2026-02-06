package com.svalero.komorebiApi.exception;


public class SchoolNotFoundException extends Exception {

    public SchoolNotFoundException() {
        super("This school does not exist");
    }

    public SchoolNotFoundException(String message) {
        super(message);
    }
}
