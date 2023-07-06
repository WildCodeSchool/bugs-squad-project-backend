package com.templateproject.api.service;

import com.templateproject.api.config.JwtService;
import com.templateproject.api.controller.LoginRequest;
import com.templateproject.api.controller.RegisterRequest;
import com.templateproject.api.entity.User;
import com.templateproject.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authentificationManager;

    public void signup(RegisterRequest registerRequest) {
        var user = User.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();
        userRepository.save(user);

    }

    public void login(LoginRequest loginRequest) {
        authentificationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword())
        );
        var user = userRepository.findByEmail(loginRequest.getEmail())
        .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public void logout(LoginRequest loginRequest) {
        var user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}

