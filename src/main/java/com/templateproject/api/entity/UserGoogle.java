package com.templateproject.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserGoogle extends User {

    @Column(name = "google_id")
    private String googleId;

    @Column(name = "google_email")
    private String googleEmail;

    private String password = null;

    private Integer sub; // Google's unique identifier for the user

    public UserGoogle(String username, String email, String googleId, String googleEmail, String password, Set<Role> roles) {
        super(username, email, roles);
        this.googleId = googleId;
        this.googleEmail = googleEmail;
        this.password = password;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getGoogleEmail() {
        return googleEmail;
    }

    public void setGoogleEmail(String googleEmail) {
        this.googleEmail = googleEmail;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

}

