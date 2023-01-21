package com.recipiesbook.services.impl;

import com.recipiesbook.model.Recipe;
import com.recipiesbook.services.RecipeService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {
    public Map<Integer, Recipe> recipeMap = new LinkedHashMap<>();
    public static int id = 0;

    @Override
    public int addRecipe(Recipe recipe) {
        recipeMap.put(id++, recipe);
        return id;
    }

    @Override
    public Recipe getRecipe(int id) {
        if (recipeMap.containsKey(id) && id > 0) {
            return recipeMap.get(id);
        }
        return null;
    }

    @Override
    public Map<Integer, Recipe> getAllRecipe() {
        if (!recipeMap.isEmpty()) {
            return recipeMap;
        }
        return null;
    }

    @Override
    public Recipe editRecipe(int id, Recipe recipe) {
        if (recipeMap.containsKey(id)) {
            recipeMap.put(id, recipe);
            return recipe;
        }
        return null;
    }

    @Override
    public boolean deleteRecipe(int id) {
        if (recipeMap.containsKey(id)) {
            recipeMap.remove(id);
            return true;
        }
        return false;
    }
}
