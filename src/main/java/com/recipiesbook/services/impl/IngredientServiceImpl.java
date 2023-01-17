package com.recipiesbook.services.impl;

import com.recipiesbook.model.Ingredients;
import com.recipiesbook.services.IngredientService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {

    Map<Integer, Ingredients> ingredientsMap = new LinkedHashMap<>();
    public static int id = 0;

    @Override
    public void addIngredient(String nameIngredient, int count, String units) {
        ingredientsMap.put(id++, new Ingredients(nameIngredient, count, units));
    }

    @Override
    public Ingredients getIngredient(int id) {
        if (ingredientsMap.containsKey(id) && id > 0) {
            return ingredientsMap.get(id);
        } else {
            throw new IllegalArgumentException("Данного ингредиента не существует.");
        }
    }
}
