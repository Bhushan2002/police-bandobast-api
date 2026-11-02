package com.app.policebandobastapi.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resource, int id) {
        super(resource + " not found with id " + id);
    }
}
