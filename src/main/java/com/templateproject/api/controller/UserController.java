package com.templateproject.api.controller;

import com.templateproject.api.entity.User;
import com.templateproject.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/users")
    public <List> User getAllUsers() {
        return (User) userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public void getUserById(@PathVariable String id) {
        userRepository.findById(Long.parseLong(id));
    }



        @PutMapping("/users/{id}")
        public User updateUser (@PathVariable Long id, @RequestBody User updatedUser){
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable."));
            user.setEmail(updatedUser.getEmail());
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            user.setFirstname(updatedUser.getFirstname());
            user.setLastname(updatedUser.getLastname());

            return userRepository.save(user);
        }


    }
