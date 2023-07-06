package com.templateproject.api.controller;

import io.swagger.v3.oas.annotations.info.Contact;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private static String authenticationToken;
    private String email;
    private String firstname;
    private String lastname;
    private String role;
    private String refreshToken;
    private Contact contact;

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken) {
        AuthenticationResponse.authenticationToken = authenticationToken;
    }

}
