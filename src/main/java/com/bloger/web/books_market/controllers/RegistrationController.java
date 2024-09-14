package com.bloger.web.books_market.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.bloger.web.books_market.services.RegistrationService;

@Controller
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping ("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping ("/registration")
    public String registration(
            @RequestParam("login") String login,
            @RequestParam("password") String password) {
        registrationService.addCredential(login, password);
        return "registration-success";
    }
}