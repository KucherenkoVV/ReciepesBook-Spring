package com.recipiesbook.controllers;

import com.recipiesbook.model.Recipe;
import com.recipiesbook.services.impl.RecipeServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private RecipeServiceImpl recipeService;

    public RecipeController(RecipeServiceImpl recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable int id) {
        Recipe recipe = recipeService.getRecipe(id);
        if (recipe == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("/getAll")
    public ResponseEntity<Map<Integer,Recipe>> getAllRecipes(){
        Map<Integer, Recipe> allRecipe = recipeService.getAllRecipe();
        if(allRecipe == null){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allRecipe);
    }

    @PostMapping("/add")
    public ResponseEntity<Integer> addRecipe(@RequestBody Recipe recipe) {
        int id = recipeService.addRecipe(recipe);
        return ResponseEntity.ok().body(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> editRecipe(@PathVariable int id, @RequestBody Recipe recipe) {
        Recipe newRecipe = recipeService.editRecipe(id, recipe);
        if (newRecipe == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newRecipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable int id) {
        if (recipeService.deleteRecipe(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
