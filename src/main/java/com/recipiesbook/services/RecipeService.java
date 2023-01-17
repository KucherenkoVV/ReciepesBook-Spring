package com.recipiesbook.services;

import com.recipiesbook.model.Ingredients;
import com.recipiesbook.model.Recipe;

import java.util.List;
import java.util.Map;

public interface RecipeService {

    int addRecipe(Recipe recipe);

    Recipe getRecipe(int id);

    Map<Integer, Recipe> getAllRecipe();

    Recipe editRecipe(int id, Recipe recipe);

    boolean deleteRecipe(int id);
}
