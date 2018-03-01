package com.project.home24.dto.account;

import javax.validation.constraints.NotNull;

public class CreateAccountRequest {

    @NotNull(message = "Field email should not be empty")
    private String email;

    @NotNull(message = "Field password should not be empty")
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
