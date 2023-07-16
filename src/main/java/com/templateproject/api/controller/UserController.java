package com.templateproject.api.controller;

import com.templateproject.api.entity.User;
import com.templateproject.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {this.userService = userService;}

    // after login get the principal from the authentication object
    @GetMapping("/")
    public String index(Authentication authentication) {
        return "Hello " + authentication.getName();
    }

    @Operation(summary = "Find users", description = "Find all users")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/users/")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @Operation(summary = "Find user", description = "Find a user")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping("/auth/register")
    public User register(String username, String password, String email) {
        return userService.register(username, password, email);
    }

    @PostMapping("/auth/login")
    public String login(String email, String password) {
        return userService.login(email, password);
    }

}