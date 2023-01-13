package com.recipiesbook.services;

import com.recipiesbook.model.Ingredients;
import com.recipiesbook.model.Recipe;

import java.util.List;

public interface RecipeService {

    void addRecipe(String nameRecipe, int minutes, List<Ingredients> ingredientsList, List<String> steps);

    Recipe getRecipe(int id);
}
