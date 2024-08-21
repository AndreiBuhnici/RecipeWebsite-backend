package com.summer_project_backend.service;

import com.summer_project_backend.dto.IngredientDTO;
import com.summer_project_backend.exception.NotFoundException;
import com.summer_project_backend.model.Ingredient;
import com.summer_project_backend.repository.IngredientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository, ModelMapper modelMapper) {
        this.ingredientRepository = ingredientRepository;
        this.modelMapper = modelMapper;
    }

    public void addIngredient(IngredientDTO ingredientDTO) {
        Ingredient ingredient = new Ingredient(ingredientDTO.getName(), null);
        ingredientRepository.save(ingredient);
    }

    public List<IngredientDTO> getAllIngredients() {
        return ingredientRepository.findAll().stream().map(tag -> modelMapper.map(tag, IngredientDTO.class)).toList();
    }

    public void deleteIngredient(UUID ingredientId) {
        Optional<Ingredient> ingredient = ingredientRepository.findById(ingredientId);
        if (ingredient.isEmpty())
            throw new NotFoundException("Ingredient doesn't exist");

        ingredientRepository.deleteById(ingredientId);
    }
}
