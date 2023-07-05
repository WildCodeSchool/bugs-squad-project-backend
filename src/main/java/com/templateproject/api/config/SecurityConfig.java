package com.templateproject.api.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    protected void securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests(
                        authorize -> authorize
                                .requestMatchers(HttpMethod.GET, "/api/**").hasRole("USER")
                                .requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )


        @Bean
     public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                    .withUser("user").password("password").roles("USER")
                    .and()
                    .withUser("admin").password("password").roles("USER", "ADMIN");
        }

        @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

