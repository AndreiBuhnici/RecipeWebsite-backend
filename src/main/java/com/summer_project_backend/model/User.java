package com.summer_project_backend.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "user", schema = "public")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable = false)
    private UUID id;
    @Column(name="name", nullable = false)
    private String name;
    @Column(name="email", nullable = false)
    private String email;
    @Column(name="password", nullable = false)
    private String password;
    @Column(name="role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name="verified", nullable = false)
    private boolean isVerified;
    @OneToMany(mappedBy = "user")
    private List<Post> posts;
    @CreatedDate
    @Column(name="created_at", updatable = false)
    private Date createdAt;
    @LastModifiedDate
    @Column(name="updated_at")
    private Date updatedAt;

    public User() {}

    public User(String name, String email, String password, Role role, boolean isVerified, List<Post> posts) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isVerified = isVerified;
        this.posts = posts;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /*
        List<SimpleGrantedAuthority> authorities;
        if (role == Role.ADMIN) {
            authorities = (Arrays.stream(Role.values()).map(e -> new SimpleGrantedAuthority(e.toString())).toList());
        } else {
            authorities = List.of(new SimpleGrantedAuthority(role.toString()));
        }


        return authorities;

         */
        return List.of(new SimpleGrantedAuthority(role.toString()));
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public void setVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
