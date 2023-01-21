package com.recipiesbook.services;
import com.recipiesbook.model.Ingredients;

import java.util.Map;

public interface IngredientService {

    int addIngredient(Ingredients ingredients);

    Ingredients getIngredient(int id);

    Map<Integer, Ingredients> getAllIngredients();

    Ingredients editIngredient(int id, Ingredients ingredient);

    boolean deleteIngredient(int id);
}
