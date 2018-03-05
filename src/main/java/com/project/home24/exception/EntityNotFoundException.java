package com.project.home24.exception;

import org.springframework.web.client.RestClientException;

public class EntityNotFoundException extends RestClientException {

    public EntityNotFoundException(String id) {
        super(getMessage(id));
    }

    private static String getMessage(String id) {
        return String.format("Entity not found with id - %s", id);
    }
}
