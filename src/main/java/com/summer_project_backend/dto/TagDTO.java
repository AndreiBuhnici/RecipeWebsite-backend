package com.summer_project_backend.dto;

public class TagDTO {
    private String name;

    public TagDTO() {}

    public TagDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}