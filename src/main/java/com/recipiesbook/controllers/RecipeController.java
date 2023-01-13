package com.recipiesbook.controllers;

import com.recipiesbook.model.Ingredients;
import com.recipiesbook.model.Recipe;
import com.recipiesbook.services.impl.RecipeServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private RecipeServiceImpl recipeService;

    public RecipeController(RecipeServiceImpl recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/get")
    public Recipe getRecipe(@RequestParam int id){
        return recipeService.getRecipe(id);
    }

    @GetMapping("/add")
    public String addRecipe(@RequestParam String name,@RequestParam int min,@RequestParam List<Ingredients> ingrList, @RequestParam List<String> steps) {
        recipeService.addRecipe(name, min, ingrList, steps);
        return "Рецепт " +name +" добавлен";
    }
}
