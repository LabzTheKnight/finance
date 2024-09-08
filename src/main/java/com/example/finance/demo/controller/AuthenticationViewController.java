package com.example.finance.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.finance.demo.service.CustomUserDetailsService;
import com.example.finance.demo.model.User;
import com.example.finance.demo.security.JwtRequest;

@Controller
public class AuthenticationViewController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    // Render the login page
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute JwtRequest JwtRequest) {
        customUserDetailsService.loginUser(JwtRequest.getUsername(), JwtRequest.getPassword());
        return "redirect:/users";
    }

    // Render the register page
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Register a new user
    @PostMapping("/register")
    public String register(@ModelAttribute JwtRequest jwtRequest) {
        customUserDetailsService.registerUser(jwtRequest.getUsername(), jwtRequest.getPassword());
        return "redirect:/login";
    }

    // Render the user list page
    @GetMapping("/users")
    public String showUsers(Model model) {
        List<User> users = customUserDetailsService.findAll();
        model.addAttribute("users", users);
        return "user-list";
    }
}