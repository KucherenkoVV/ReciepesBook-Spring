package com.recipiesbook.controllers;

import com.recipiesbook.exception.NoFindException;
import com.recipiesbook.model.Ingredients;
import com.recipiesbook.model.Recipe;
import com.recipiesbook.services.impl.RecipeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Month;
import java.util.Map;

@RestController
@RequestMapping("/recipe")
@Tag(name = "Рецепт", description = "CRUD операции для работы с рецептами.")
public class RecipeController {

    private RecipeServiceImpl recipeService;

    public RecipeController(RecipeServiceImpl recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Поиск рецепта по его id")
    @Parameters(value = {
            @Parameter(name = "id", example = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Рецепт найден."),
            @ApiResponse(responseCode = "400",
                    description = "Ошибка в параметрах запроса."),
            @ApiResponse(responseCode = "404",
                    description = "Некорректный URL."),
            @ApiResponse(responseCode = "500",
                    description = "Ошибка на стороне сервера.")
    }
    )
    public ResponseEntity<Recipe> getRecipe(@PathVariable int id) throws NoFindException {
        Recipe recipe = recipeService.getRecipe(id);
        if (recipe == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("/getAll")
    @Operation(summary = "Вывод всех рецептов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Выведен список рецептов."),
            @ApiResponse(responseCode = "404",
                    description = "Некорректный URL."),
            @ApiResponse(responseCode = "500",
                    description = "Ошибка на стороне сервера.")
    }
    )
    public ResponseEntity<Map<Integer, Recipe>> getAllRecipes() throws NoFindException {
        Map<Integer, Recipe> allRecipe = recipeService.getAllRecipe();
        if (allRecipe == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allRecipe);
    }

    @GetMapping(value = "/report", produces = MediaType.TEXT_PLAIN_VALUE)
    @Operation(summary = "Отчет по рецептам в формате .txt")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Сформирован отчет по рецептам."),
            @ApiResponse(responseCode = "404",
                    description = "Некорректный URL."),
            @ApiResponse(responseCode = "500",
                    description = "Ошибка на стороне сервера.")
    }
    )
    public ResponseEntity<Object> getRecipeReport() {
        try {
            Path path = recipeService.createRecipeReport();
            if (Files.size(path) == 0) {
                return ResponseEntity.noContent().build();
            }
            InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile()));
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(Files.size(path))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "-report.txt\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.toString());
        }
    }


    @PostMapping("/add")
    @Operation(summary = "Добавление рецептов.", description = "Добавление рецепта по его параметрам.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Рецепт добавлен.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))
                    }
            ),
            @ApiResponse(responseCode = "400",
                    description = "Ошибка в параметрах запроса."),
            @ApiResponse(responseCode = "404",
                    description = "Некорректный URL."),
            @ApiResponse(responseCode = "500",
                    description = "Ошибка на стороне сервера.")
    }
    )
    public ResponseEntity<Integer> addRecipe(@RequestBody Recipe recipe) throws NoFindException {
        int id = recipeService.addRecipe(recipe);
        return ResponseEntity.ok().body(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактирование рецепта по id.", description = "Поиск рецепта по его id и добавление нового по заданным параметрам.")
    @Parameters(value = {
            @Parameter(name = "id", example = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Рецепт отредактирован.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))
                    }
            ),
            @ApiResponse(responseCode = "400",
                    description = "Ошибка в параметрах запроса."),
            @ApiResponse(responseCode = "404",
                    description = "Некорректный URL."),
            @ApiResponse(responseCode = "500",
                    description = "Ошибка на стороне сервера.")
    }
    )
    public ResponseEntity<Recipe> editRecipe(@PathVariable int id, @RequestBody Recipe recipe) throws NoFindException {
        Recipe newRecipe = recipeService.editRecipe(id, recipe);
        if (newRecipe == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newRecipe);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление рецепта по id.")
    @Parameters(value = {
            @Parameter(name = "id", example = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Рецепт удален."
            ),
            @ApiResponse(responseCode = "400",
                    description = "Ошибка в параметрах запроса."),
            @ApiResponse(responseCode = "404",
                    description = "Некорректный URL."),
            @ApiResponse(responseCode = "500",
                    description = "Ошибка на стороне сервера.")
    }
    )
    public ResponseEntity<Void> deleteRecipe(@PathVariable int id) throws NoFindException {
        if (recipeService.deleteRecipe(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
