package com.templateproject.api.controller;

import com.templateproject.api.entity.User;
import com.templateproject.api.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class OAuthController {

    private final UserRepository userRepository;

    public OAuthController(UserRepository userRepository, OAuth2AuthorizedClientService clientService) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public String registerUserWithOAuth2() {
        OAuth2AuthorizationRequest authorizationRequest = OAuth2AuthorizationRequest
                .authorizationCode()
                .authorizationUri("<authorization URL>")
                .clientId("<client ID>")
                .redirectUri("<redirect URL>")
                .build();

        return "redirect:" + authorizationRequest.getAuthorizationRequestUri();
    }

    @PostMapping("/login")
    public User loginUserWithOAuth2(@AuthenticationPrincipal OAuth2User oauth2User) {
        String email = oauth2User.getAttribute("email");
        String firstName = oauth2User.getAttribute("given_name");
        String lastName = oauth2User.getAttribute("family_name");

        Optional<Object> optionalUser = userRepository.findByEmail(email);

        return (User) optionalUser.orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setFirstname(firstName);
            newUser.setLastname(lastName);
            return userRepository.save(newUser);
        });
    }
    @GetMapping("/logout")
    public String logoutUserWithOAuth2() {
        return "redirect:/logout";
    }

}
