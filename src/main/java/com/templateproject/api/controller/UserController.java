package com.templateproject.api.controller;

import com.templateproject.api.entity.LoginRequest;
import com.templateproject.api.entity.LoginResponse;
import com.templateproject.api.entity.User;
import com.templateproject.api.entity.UserGoogle;
import com.templateproject.api.repository.UserRepository;
import com.templateproject.api.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;


    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
    }

    @PostMapping("/auth/register")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }


    @PostMapping("/auth/register/google")
    public User createUserGoogle(@RequestBody UserGoogle userGoogle) {
        return userService.createUserGoogle(userGoogle);
    }
    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        return userService.login(email, password);
    }


}