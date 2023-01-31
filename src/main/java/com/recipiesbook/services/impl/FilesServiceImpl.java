package com.recipiesbook.services.impl;

import com.recipiesbook.exception.NotFindFileException;
import com.recipiesbook.services.FilesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesServiceImpl implements FilesService {

    @Value("${path.to.data.file}")
    private String dataFilePath;

    @Value("${name.of.recipe.data.file}")
    private String recipeFileName;

    @Value("${name.of.ingredient.data.file}")
    private String ingredientFileName;

    @Override
    public boolean saveToFile(String json, String dataFileName) {
        Path path = Path.of(dataFilePath,dataFileName);
        try {
            cleanDataFile(dataFileName);
            Files.writeString(path, json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readFromFile(String dataFileName)  {
        Path path = Path.of(dataFilePath, dataFileName);
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean cleanDataFile(String dataFileName) {
        try {
            Path path = Path.of(dataFilePath, dataFileName);
                Files.deleteIfExists(path);
                Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public File getDataFile(){
        return new File(dataFilePath +"/" +recipeFileName);
    }

}
