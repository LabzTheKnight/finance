package com.example.finance.demo.controller;

import com.example.finance.demo.model.Category;
import com.example.finance.demo.model.DefaultCategory;
import com.example.finance.demo.model.User;
import com.example.finance.demo.model.UserCategory;
import com.example.finance.demo.repository.UserRepository;
import com.example.finance.demo.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserRepository userRepository;

    // Endpoint to get all categories for a specific user (both default and user-specific)
    @GetMapping("/user/{username}")
    public ResponseEntity<List<Category>> getAllCategoriesForUser(@PathVariable String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found" + username));  
        List<Category> categories = categoryService.getAllCategoriesForUser(user);
        return ResponseEntity.ok(categories);
    }

    // Endpoint to get all default categories
    @GetMapping("/default/all")
    public ResponseEntity<List<DefaultCategory>> getAllDefaultCategories() {
        List<DefaultCategory> categories = categoryService.getAllDefaultCategories();
        return ResponseEntity.ok(categories);
    }

    // Endpoint to get a default category by its ID
    @GetMapping("/default/{id}")
    public ResponseEntity<DefaultCategory> getDefaultCategoryById(@PathVariable Long id) {
        DefaultCategory category = categoryService.getDefaultCategoryById(id);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(category);
    }

    // Endpoint to add a new default category
    @PostMapping("/default/add")
    public ResponseEntity<DefaultCategory> addDefaultCategory(@RequestBody DefaultCategory category) {
        DefaultCategory savedCategory = categoryService.saveDefaultCategory(category);
        return ResponseEntity.ok(savedCategory);
    }

    // Endpoint to delete a default category by its ID
    @DeleteMapping("/default/{id}")
    public ResponseEntity<Void> deleteDefaultCategory(@PathVariable Long id) {
        categoryService.deleteDefaultCategory(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint to get all user-specific categories for a specific user
    @GetMapping("/user/categories/{username}/all")
    public ResponseEntity<List<UserCategory>> getUserCategoriesByUser(@PathVariable String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found")); 
        List<UserCategory> categories = categoryService.getUserCategoriesByUser(user);
        return ResponseEntity.ok(categories);
    }

    // Endpoint to get a user-specific category by its ID
    @GetMapping("/user/category/{id}")
    public ResponseEntity<UserCategory> getUserCategoryById(@PathVariable Long id) {
        UserCategory category = categoryService.getUserCategoryById(id);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(category);
    }

    @PostMapping("/user/add")
    public ResponseEntity<UserCategory> addUserCategory(
        @RequestParam String categoryName, 
        @RequestParam String username) {
    
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("User not found"));
    
    UserCategory category = new UserCategory();
    category.setName(categoryName);  
    category.setUser(user);  

    UserCategory savedCategory = categoryService.saveUserCategory(category);
    
    return ResponseEntity.ok(savedCategory);
}

    // Endpoint to delete a user-specific category by its ID
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUserCategory(@PathVariable Long id) {
        categoryService.deleteUserCategory(id);
        return ResponseEntity.noContent().build();
    }

    
}