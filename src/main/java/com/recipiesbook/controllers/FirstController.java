package com.recipiesbook.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;

@RestController
public class FirstController {

    @GetMapping
    public String startApp(){
        return "Приложение запущено.";
    }

    @GetMapping("/info")
    public String info(){
        return "Автор: Кучеренко Виталий.\n"+
                "Название проекта: Recipes Book.\n" +
                "Дата создания проекта " + LocalDate.now()+ ".\n"+
                "Проект написан с использованием языка Java, Spring framework и сборщика Maven.";

    }
}
