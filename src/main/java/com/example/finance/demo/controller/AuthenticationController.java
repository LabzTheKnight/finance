package com.example.finance.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;

import com.example.finance.demo.model.User;
import com.example.finance.demo.service.RegistrationService;
import com.example.finance.demo.security.JwtTokenGenerator;
@RestController
@RequestMapping("api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private JwtTokenGenerator jwtTokenGenerator;

    // Handle login via API
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            // Get the authenticated user's details
            String username = authentication.getName();
            // Generate a token (e.g., JWT)
            String token = jwtTokenGenerator.generateToken(username);
            // Return the token and success message
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).body("Login successful for user " + username);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    // Handle user registration via API 
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            registrationService.registerUser(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Username already taken");
        }
    }

    // Handle logout success message
    @GetMapping("/logout-success")
    public ResponseEntity<String> logoutSuccess() {
        return ResponseEntity.ok("You have been logged out successfully");
    }
}
