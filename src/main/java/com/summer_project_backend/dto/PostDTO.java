package com.summer_project_backend.dto;

import java.util.List;
import java.util.UUID;

public class PostDTO {
    private UUID id;
    private String title;
    private List<TagDTO> tags;
    private List<IngredientDTO> ingredients;
    private String description;
    private String recipe;

    public PostDTO() {}

    public PostDTO(UUID id, String title, List<TagDTO> tags, List<IngredientDTO> ingredients, String description,
                   String recipe) {
        this.id = id;
        this.title = title;
        this.tags = tags;
        this.ingredients = ingredients;
        this.description = description;
        this.recipe = recipe;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public List<IngredientDTO> getIngredients() {
        return ingredients;
    }

    public String getDescription() {
        return description;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
    }

    public void setIngredients(List<IngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }
}
