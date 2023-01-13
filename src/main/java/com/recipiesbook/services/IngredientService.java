package com.recipiesbook.services;
import com.recipiesbook.model.Ingredients;

public interface IngredientService {

    void addIngredient(String nameIngredient, int count, String units);

    Ingredients getIngredient(int id);
}
