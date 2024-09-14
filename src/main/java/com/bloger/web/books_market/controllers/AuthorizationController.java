package com.bloger.web.books_market.controllers;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import com.bloger.web.books_market.services.RegistrationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class AuthorizationController {

    @Autowired
    private final RegistrationService registrationService;

    public AuthorizationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/auth")
    public String authorization(Model model) {
        return "auth";
    }

    @GetMapping ("/authorization")
    public String authorization() {
        return "authorization";
    }

    @PostMapping("/authorization")
    public String authorization(
            @RequestParam("login") String login,
            @RequestParam("password") String password) {
        System.out.println(registrationService.getAllCredentials());
        if (registrationService.isAdministrator(login, password)) {
            return "main-admin";
        }

        if (registrationService.isAuthorized(login, password)) {
            registrationService.logOut(login, password);
            return "authorization-success";
        }
        return "authorization";
    }
}