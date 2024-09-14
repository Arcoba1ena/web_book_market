package com.bloger.web.books_market.controllers;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/main")
    public String mainPage(Model model) {
        model.addAttribute("page", "Главная страница");
        return "main";
    }

    @GetMapping("/main/admin")
    public String mainAdminPage(Model model) {
        model.addAttribute("page", "Главная страница");
        return "main-admin";
    }
}
