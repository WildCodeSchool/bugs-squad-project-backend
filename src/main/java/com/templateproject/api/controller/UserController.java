package com.templateproject.api.controller;

import com.templateproject.api.entity.User;
import com.templateproject.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {this.userService = userService;}


    @PostMapping("/auth/register")
    public User register(String username, String password, String email) {
        return userService.register(username, password, email);
    }

    @PostMapping("/auth/login")
    public String login(String email, String password) {
        return userService.login(email, password);
    }

}