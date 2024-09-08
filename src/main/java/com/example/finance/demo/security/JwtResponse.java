package com.example.finance.demo.security;


public class JwtResponse {
    private String token;


    // Default constructor
    public JwtResponse() {}


    // Parameterized constructor
    public JwtResponse(String token) {
        this.token = token;
    }


    // Getter and Setter
    public String getToken() {
        return token;
    }


    public void setToken(String token) {
        this.token = token;
    }
}
