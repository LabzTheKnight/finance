package com.example.finance.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.finance.demo.service.CustomUserDetailsService;


@RestController
@RequestMapping("/api/users")
public class AuthController {
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<String> loginUser() {
        try{
        userDetailsService.loadUserByUsername("user");
        return ResponseEntity.ok("User logged in successfully");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser() {
        try {
            userDetailsService.registerUser("user", "password");
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        
    }
    
}
