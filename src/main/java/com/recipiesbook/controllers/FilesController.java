package com.recipiesbook.controllers;

import com.recipiesbook.model.Recipe;
import com.recipiesbook.services.FilesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/files")
@Tag(name = "Файловый контроллер.", description = "Для работы с файлами рецептов и ингредиентов.")
public class FilesController {

    @Value("${name.of.recipe.data.file}")
    private String recipeFileName;

    @Value("${name.of.ingredient.data.file}")
    private String ingredientFileName;

    private final FilesService filesService;

    public FilesController(FilesService filesService) {
        this.filesService = filesService;
    }

    @GetMapping(value = "/export")
    @Operation(summary = "Скачивание файла рецептов в формате JSON.")
    public ResponseEntity<InputStreamResource> downloadDataFile() throws FileNotFoundException {
        File file = filesService.getDataFile();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"recipe.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "/importRecipe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Импорт рецептов.", description = "Импортирование файла рецептов в формате .json.")
    @ApiResponse(responseCode = "200", description = "Файл загружен.")
    public ResponseEntity<Void> uploadDataFileRecipe(@RequestParam MultipartFile file) {
        filesService.cleanDataFile(recipeFileName);
        return getVoidResponseEntity(file);
    }

    @PostMapping(value = "/importIngredient", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Импорт ингредиентов.", description = "Импортирование файла ингредиентов в формате .json.")
    @ApiResponse(responseCode = "200", description = "Файл загружен.")
    public ResponseEntity<Void> uploadDataFileIngredient(@RequestParam MultipartFile file) {
        filesService.cleanDataFile(ingredientFileName);
        return getVoidResponseEntity(file);
    }

    private ResponseEntity<Void> getVoidResponseEntity(@RequestParam MultipartFile file) {
        File dataFile = filesService.getDataFile();
        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
