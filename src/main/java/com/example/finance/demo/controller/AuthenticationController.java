package com.example.finance.demo.controller;

import com.example.finance.demo.service.CustomUserDetailsService;
import com.example.finance.demo.security.JwtRequest;
import com.example.finance.demo.security.JwtResponse;
import com.example.finance.demo.security.JwtUtil;
import com.example.finance.demo.model.User;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/users")
@RestController
public class AuthenticationController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public JwtResponse createAuthenticationToken(@RequestBody JwtRequest JwtRequest) {
        final UserDetails userDetails = customUserDetailsService.loginUser(JwtRequest.getUsername(), JwtRequest.getPassword());
        final String jwt = jwtUtil.generateToken(userDetails);

        return new JwtResponse(jwt);
    }

    @PostMapping("/register")
    public User register(@RequestBody JwtRequest jwtRequest) {
        return customUserDetailsService.registerUser(jwtRequest.getUsername(), jwtRequest.getPassword());
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return customUserDetailsService.findAll();
    }
}