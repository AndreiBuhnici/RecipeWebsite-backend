package com.summer_project_backend.repository;

import com.summer_project_backend.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
    Tag findByName(@NonNull String name);
}
