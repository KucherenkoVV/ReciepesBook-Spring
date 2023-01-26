package com.recipiesbook.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class Recipe {
    @NotBlank(message = "Name is empty")
    private String nameRecipe;
    @Positive
    private int minutes;
    @NotEmpty
    private List <Ingredients> ingredientsList;
    @NotEmpty
    private List<String> steps;

}
