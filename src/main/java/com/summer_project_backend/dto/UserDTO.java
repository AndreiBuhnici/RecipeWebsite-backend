package com.summer_project_backend.dto;

import com.summer_project_backend.model.Role;

import java.util.List;
import java.util.UUID;

public class UserDTO {
    private UUID id;
    private String name;
    private String email;
    private Role role;
    private boolean isVerified;

    public UserDTO() {}

    public UserDTO(UUID id, String name, String email, Role role, boolean isVerified) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.isVerified = isVerified;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }
}
