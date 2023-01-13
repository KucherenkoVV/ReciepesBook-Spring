package com.recipiesbook.controllers;

import com.recipiesbook.model.Ingredients;
import com.recipiesbook.services.IngredientService;
import com.recipiesbook.services.impl.IngredientServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private IngredientServiceImpl ingredientService;

    public IngredientController(IngredientServiceImpl ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/get")
    public Ingredients getIngredient(@RequestParam int id){
        return ingredientService.getIngredient(id);
    }

    @GetMapping("/add")
    public String addIngredient(@RequestParam String name, @RequestParam int count, @RequestParam String units){
        ingredientService.addIngredient(name, count, units);
        return "Ингридиент " +name +" добавлен";

    }
}
