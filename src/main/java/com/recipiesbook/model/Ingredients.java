package com.recipiesbook.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class Ingredients {
    @NotBlank(message = "Name is empty")
    private String nameIngredient;
    @Positive
    private int count;
    private String units;

}
