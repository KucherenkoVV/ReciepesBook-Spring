package com.recipiesbook.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
public class Ingredients {
    private String nameIngredient;
    private int count;
    private String units;

}
