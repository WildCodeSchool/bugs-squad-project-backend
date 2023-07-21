package com.templateproject.api.service;


import com.templateproject.api.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class TokenService {

    private final JwtEncoder jwtEncoder;

    public TokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    //Use Authentication class from security ???
    public String generateToken(Authentication auth) {
        // We want to create a JWT token
        // Choose and set the algorithm used to sign the token (here HS256) and build the header
        JwsHeader jwsHeader = JwsHeader.with(() -> "HS256").build();

        // Retrieve the timestamp of the moment the token is created
        Instant now = Instant.now();

        // We want to set the expiration date of the token to 30 days after its creation
        Instant expiration = now.plusSeconds(30 * 24 * 60 * 60);

        // We want to set the scope of the token to the authorities of the user
        String scope = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        // We want to set the claims of the token
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(expiration)
                .subject(auth.getName())
                .claim("scope", scope)
                .claim("username", ((User) auth.getPrincipal()).getUserStringName())
                .build();

        // We want to encode the token with the header and the payload
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();

    }

}