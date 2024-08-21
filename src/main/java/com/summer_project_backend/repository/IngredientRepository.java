package com.summer_project_backend.repository;

import com.summer_project_backend.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.UUID;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {
    Ingredient findByName(@NonNull String name);
}
