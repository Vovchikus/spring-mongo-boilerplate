package com.project.boilerplate.dto.entity;

import javax.validation.constraints.NotNull;

public class EntityRequest {

    @NotNull(message = "Field title should not be empty")
    private String title;

    @NotNull(message = "Field description should not be empty")
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
