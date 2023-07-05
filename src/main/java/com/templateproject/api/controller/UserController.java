package com.templateproject.api.controller;

import com.templateproject.api.entity.User;
import com.templateproject.api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users")
    public <List> User getAllUsers() {
        return (User) userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public void getUserById(@PathVariable String id) {
        userRepository.findById(Long.parseLong(id));
    }

    @PostMapping("/register")
    public User createUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @PostMapping("/login")
    public Optional<Object> login(@RequestBody User user) {
        Optional<Object> userFound = userRepository.findByEmail(user.getEmail());
    if (userFound.isPresent()) {
            User userToCheck = (User) userFound.get();
            if (passwordEncoder.matches(user.getPassword(), userToCheck.getPassword())) {
                return userFound;
            } else {
                throw new UsernameNotFoundException("Mot de passe incorrect.");
            }
        } else {
            throw new UsernameNotFoundException("Utilisateur introuvable.");
        }
    }

    @DeleteMapping("/users/{id}")
    public HttpStatus deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return HttpStatus.OK;
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable."));
        user.setEmail(updatedUser.getEmail());
        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        user.setFirstname(updatedUser.getFirstname());
        user.setLastname(updatedUser.getLastname());

        return userRepository.save(user);
    }




}
