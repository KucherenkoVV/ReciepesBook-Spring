package com.recipiesbook.controllers;

import com.recipiesbook.model.Ingredients;
import com.recipiesbook.services.impl.IngredientServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private IngredientServiceImpl ingredientService;

    public IngredientController(IngredientServiceImpl ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredients> getIngredient(@PathVariable int id) {
        Ingredients ingredient = ingredientService.getIngredient(id);
        if (ingredient == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    @GetMapping("/getAll")
    public ResponseEntity<Map<Integer,Ingredients>> getAllIngredients(){
        Map<Integer, Ingredients> allIngredients = ingredientService.getAllIngredients();
        if(allIngredients == null){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allIngredients);
    }

    @PostMapping()
    public ResponseEntity<Integer> addIngredient(@RequestBody Ingredients ingredients) {
        int id = ingredientService.addIngredient(ingredients);
        return ResponseEntity.ok().body(id);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredients> editIngredient(@PathVariable int id, @RequestBody Ingredients ingredients) {
        Ingredients editIngredient = ingredientService.editIngredient(id, ingredients);
        if (editIngredient == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(editIngredient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable int id) {
        if (ingredientService.deleteIngredient(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
