package com.recipiesbook.services.impl;

import com.recipiesbook.model.Ingredients;
import com.recipiesbook.model.Recipe;
import com.recipiesbook.services.RecipeService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {
    Map<Integer, Recipe> recipeMap = new LinkedHashMap<>();
    public static int id = 0;

    @Override
    public void addRecipe(String nameRecipe, int minutes, List<Ingredients> ingredientsList, List<String> steps){
        recipeMap.put(id++, new Recipe(nameRecipe, minutes, ingredientsList, steps));
    }

    @Override
    public Recipe getRecipe(int id){
        if(recipeMap.containsKey(id) && id > 0){
            return recipeMap.get(id);
        } else {
            throw new IllegalArgumentException("Рецепта с данным id не существует.");
        }
    }

}
