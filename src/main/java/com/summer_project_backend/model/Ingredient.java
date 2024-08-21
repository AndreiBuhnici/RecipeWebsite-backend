package com.summer_project_backend.model;


import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ingredient", schema = "public")
@EntityListeners(AuditingEntityListener.class)
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable = false)
    private UUID id;
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToMany(mappedBy = "ingredients")
    private List<Post> posts;
    @CreatedDate
    @Column(name="created_at", updatable = false)
    private Date createdAt;
    @LastModifiedDate
    @Column(name="updated_at")
    private Date updatedAt;

    public Ingredient() {}

    public Ingredient(String name, List<Post> posts) {
        this.name = name;
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

    public List<Post> getPost() {
        return posts;
    }

    public void setPost(List<Post> posts) {
        this.posts = posts;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
