package com.recipiesbook.controllers;

import com.recipiesbook.exception.NoFindException;
import com.recipiesbook.model.Ingredients;
import com.recipiesbook.services.impl.IngredientServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ingredients")
@Tag(name = "Ингредиенты", description = "CRUD операции для работы с ингредиентами.")
public class IngredientController {

    private IngredientServiceImpl ingredientService;

    public IngredientController(IngredientServiceImpl ingredientService) {
        this.ingredientService = ingredientService;
    }


    @GetMapping("/{id}")
    @Operation(summary = "Поиск ингредиента по его id")
    @Parameters(value = {
            @Parameter(name = "id", example = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ингредиент найден."),
            @ApiResponse(responseCode = "400",
                    description = "Ошибка в параметрах запроса."),
            @ApiResponse(responseCode = "404",
                    description = "Некорректный URL."),
            @ApiResponse(responseCode = "500",
                    description = "Ошибка на стороне сервера.")
    }
    )
    public ResponseEntity<Ingredients> getIngredient(@PathVariable int id) throws NoFindException {
        Ingredients ingredient = ingredientService.getIngredient(id);
        if (ingredient == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    @GetMapping("/getAll")
    @Operation(summary = "Вывод всех ингредиентов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Выведен список ингредиентов."
            ),
            @ApiResponse(responseCode = "404",
                    description = "Некорректный URL."),
            @ApiResponse(responseCode = "500",
                    description = "Ошибка на стороне сервера.")
    }
    )
    public ResponseEntity<Map<Integer, Ingredients>> getAllIngredients() throws NoFindException {
        Map<Integer, Ingredients> allIngredients = ingredientService.getAllIngredients();
        if (allIngredients == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allIngredients);
    }

    @PostMapping("/add")
    @Operation(summary = "Добавление ингредиента.", description = "Добавление ингредиента по его параметрам.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ингридиент добавлен.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Ingredients.class))
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
    public ResponseEntity<Integer> addIngredient(@RequestBody Ingredients ingredients) throws NoFindException {
        int id = ingredientService.addIngredient(ingredients);
        return ResponseEntity.ok().body(id);

    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактирование ингредиентов по id.", description = "Поиск ингредиента по его id и добавление нового из параметров.")
    @Parameters(value = {
            @Parameter(name = "id", example = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ингридиент отредактирован.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Ingredients.class))
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
    public ResponseEntity<Ingredients> editIngredient(@PathVariable int id, @RequestBody Ingredients ingredients) throws NoFindException {
        Ingredients editIngredient = ingredientService.editIngredient(id, ingredients);
        if (editIngredient == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(editIngredient);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление ингредиента по id.")
    @Parameters(value = {
            @Parameter(name = "id", example = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ингридиент удален."
            ),
            @ApiResponse(responseCode = "400",
                    description = "Ошибка в параметрах запроса."),
            @ApiResponse(responseCode = "404",
                    description = "Некорректный URL."),
            @ApiResponse(responseCode = "500",
                    description = "Ошибка на стороне сервера.")
    }
    )
    public ResponseEntity<Void> deleteIngredient(@PathVariable int id) throws NoFindException {
        if (ingredientService.deleteIngredient(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
