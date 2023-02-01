package com.recipiesbook.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipiesbook.exception.NoFindException;
import com.recipiesbook.model.Recipe;
import com.recipiesbook.services.FilesService;
import com.recipiesbook.services.RecipeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
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
    private void init() {
        try {
            readFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int addRecipe(Recipe recipe) throws NoFindException {
        if(!recipeMap.containsValue(recipe)){
        recipeMap.put(id++, recipe);
        saveToFile();
        return id;
        } else{
            throw new NoFindException("Рецепт уже существует.");
        }
    }

    @Override
    public Recipe getRecipe(int id) throws NoFindException {
        if (recipeMap.containsKey(id) && id > 0) {
            return recipeMap.get(id);
        } else {
            throw new NoFindException("Не найден файл с данным id.");
        }
    }

    @Override
    public Map<Integer, Recipe> getAllRecipe() throws NoFindException {
        if (!recipeMap.isEmpty()) {
            return recipeMap;
        }else {
            throw new NoFindException("Список рецептов пуст.");
        }
    }

    @Override
    public Recipe editRecipe(int id, Recipe recipe) throws NoFindException {
        if (recipeMap.containsKey(id)) {
            recipeMap.put(id, recipe);
            return recipe;
        }else {
            throw new NoFindException("Не найден рецепт по id для редактирования.");
        }
    }

    @Override
    public boolean deleteRecipe(int id) throws NoFindException {
        if (recipeMap.containsKey(id)) {
            recipeMap.remove(id);
            saveToFile();
            return true;
        }else {
            throw new NoFindException("Невозможно удалить. Не найден рецепт по id.");
        }
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipeMap);
            filesService.saveToFile(json, recipeFileName);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile() {
        String json = filesService.readFromFile(recipeFileName);
        try {
            recipeMap = new ObjectMapper().readValue(json, new TypeReference<TreeMap<Integer, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Path createRecipeReport() throws IOException {
        Path path = filesService.createTempFile("RecipeReport");
        for (Recipe recipe : recipeMap.values()) {
            try (Writer writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                writer.append(recipe.getNameRecipe())
                        .append(recipe.getMinutes() + "\n")
                        .append(String.valueOf(recipe.getIngredientsList()))
                        .append("\n" + recipe.getSteps());
                writer.append("\n");
            }
        }
        return path;
    }
}
