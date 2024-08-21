package com.summer_project_backend.controller;

import com.summer_project_backend.dto.IngredientDTO;
import com.summer_project_backend.service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ingredient")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping("/addIngredient")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('USER')")
    public ResponseEntity<String> addIngredient(@RequestBody IngredientDTO ingredientDTO) {
        ingredientService.addIngredient(ingredientDTO);
        return ResponseEntity.ok("Ingredient added.");
    }

    @GetMapping("/getAllIngredients")
    public ResponseEntity<List<IngredientDTO>> getAllIngredients() {
        return ResponseEntity.ok(ingredientService.getAllIngredients());
    }

    @DeleteMapping("/deleteIngredient")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteIngredient(@RequestParam UUID ingredientId) {
        ingredientService.deleteIngredient(ingredientId);
        return ResponseEntity.ok("Ingredient deleted.");
    }
}
