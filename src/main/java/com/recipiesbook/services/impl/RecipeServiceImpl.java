package com.recipiesbook.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipiesbook.model.Recipe;
import com.recipiesbook.services.FilesService;
import com.recipiesbook.services.RecipeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.TreeMap;

@Service
public class RecipeServiceImpl implements RecipeService {
    public Map<Integer, Recipe> recipeMap = new TreeMap<>();
    public static int id = 0;

    private final FilesService filesService;

    @Value("${name.of.recipe.data.file}")
    private String recipeFileName;

    public RecipeServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }

    @PostConstruct
    private void init(){
        readFromFile();
    }

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

    private void saveToFile(){
        try {
            String json = new ObjectMapper().writeValueAsString(recipeMap);
            filesService.saveToFile(json, recipeFileName);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile(){
        String json = filesService.readFromFile(recipeFileName);
        try {
            recipeMap = new ObjectMapper().readValue(json, new TypeReference<TreeMap<Integer, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
