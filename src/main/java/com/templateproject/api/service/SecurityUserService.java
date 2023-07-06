package com.templateproject.api.service;

import com.templateproject.api.entity.User;
import com.templateproject.api.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;


@Service
public class SecurityUserService implements UserDetailsService {

   private final UserRepository userRepository;

    public SecurityUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return (User) userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(MessageFormat.format("User with email {0} cannot be found.", email)));

    }


}