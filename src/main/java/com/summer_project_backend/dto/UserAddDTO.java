package com.summer_project_backend.dto;

import com.summer_project_backend.model.Role;

public class UserAddDTO {
    private String password;
    private String email;
    private String name;
    private Role role;
    private boolean isVerified;

    public UserAddDTO() {
    }

    public UserAddDTO(String password, String email, String name, Role role, boolean isVerified) {
        this.password = password;
        this.email = email;
        this.name = name;
        this.role = role;
        this.isVerified = isVerified;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isVerified() {
        return isVerified;
    }
    public void setVerified(boolean verified) {
        isVerified = verified;
    }

}
