package com.recipiesbook.services;
import com.recipiesbook.exception.NoFindException;
import com.recipiesbook.model.Ingredients;

import java.util.Map;

public interface IngredientService {

    int addIngredient(Ingredients ingredients) throws NoFindException;

    Ingredients getIngredient(int id) throws NoFindException;

    Map<Integer, Ingredients> getAllIngredients() throws NoFindException;

    Ingredients editIngredient(int id, Ingredients ingredient) throws NoFindException;

    boolean deleteIngredient(int id) throws NoFindException;
}
