package com.templateproject.api.entity;


public class LoginResponse {
    private String token;
    private User user;

    public LoginResponse(String token, User user, Origin origin) {
        this.token = token;
        this.user = user;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}