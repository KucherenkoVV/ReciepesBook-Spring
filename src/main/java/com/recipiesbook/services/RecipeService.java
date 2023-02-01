package com.recipiesbook.services;

import com.recipiesbook.exception.NoFindException;
import com.recipiesbook.model.Ingredients;
import com.recipiesbook.model.Recipe;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface RecipeService {

    int addRecipe(Recipe recipe) throws NoFindException;

    Recipe getRecipe(int id) throws NoFindException;

    Map<Integer, Recipe> getAllRecipe() throws NoFindException;

    Recipe editRecipe(int id, Recipe recipe) throws NoFindException;

    boolean deleteRecipe(int id) throws NoFindException;

    Path createRecipeReport() throws IOException;
}
