package com.project.home24.exception;

import org.springframework.web.client.RestClientException;

public class EntityNotFoundException extends RestClientException {
    public EntityNotFoundException(Long id) {
        super(getMessage(id));
    }

    private static String getMessage(Long id) {
        return String.format("Entity not found with id - %s", id);
    }
}
