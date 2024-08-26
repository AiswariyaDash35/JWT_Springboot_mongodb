package com.practice.demo.response;

public class AuthResponse {

    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public AuthResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
