package com.summer_project_backend.repository;

import com.summer_project_backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(@NonNull String email);
    @NonNull
    Optional<User> findById(@NonNull UUID id);

    @NonNull
    Page<User> findAll(@NonNull Pageable pageable);

    Page<User> findAllByNameContaining(@NonNull String name, @NonNull Pageable pageable);
}
