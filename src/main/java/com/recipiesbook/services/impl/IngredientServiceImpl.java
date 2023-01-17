package com.recipiesbook.services.impl;

import com.recipiesbook.model.Ingredients;
import com.recipiesbook.services.IngredientService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {

    public Map<Integer, Ingredients> ingredientsMap = new LinkedHashMap<>();
    public static int id = 0;

    @Override
    public int addIngredient(Ingredients ingredients) {
        ingredientsMap.put(id++, ingredients);
        return id;
    }

    @Override
    public Ingredients getIngredient(int id) {
        for (Ingredients ingredients : ingredientsMap.values()) {
            if (ingredientsMap.containsKey(id) && id > 0) {
                return ingredientsMap.get(id);
            }
        }
        return null;
    }

    @Override
    public Map<Integer, Ingredients> getAllIngredients() {
        if (!ingredientsMap.isEmpty()) {
            return ingredientsMap;
        }
        return null;
    }

    @Override
    public Ingredients editIngredient(int id, Ingredients ingredient) {
        for (Ingredients ingredients : ingredientsMap.values()) {
            if (ingredientsMap.containsKey(id)) {
                ingredientsMap.put(id, ingredient);
                return ingredient;
            }
        }
        return null;
    }

    @Override
    public boolean deleteIngredient(int id) {
        for (Ingredients ingredients : ingredientsMap.values()) {
            if (ingredientsMap.containsKey(id)) {
                ingredientsMap.remove(id);
                return true;
            }
        }
        return false;
    }
}
