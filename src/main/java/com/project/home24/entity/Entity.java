package com.project.home24.entity;

import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

public class Entity {

    @Id
    private String id;

    private String title;

    private String description;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private String created;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private String updated;

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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
}
