package com.project.boilerplate.exception;

import org.springframework.web.client.RestClientException;

public class AccountNotFoundException extends RestClientException {

    public AccountNotFoundException(String token) {
        super(getMessage(token));
    }

    private static String getMessage(String token) {
        return String.format("Account not found with token - %s", token);
    }
}
