package com.example.finance.demo.service;

import com.example.finance.demo.model.Category;
import com.example.finance.demo.model.DefaultCategory;
import com.example.finance.demo.model.User;
import com.example.finance.demo.model.UserCategory;
import com.example.finance.demo.repository.DefaultCategoryRepository;
import com.example.finance.demo.repository.UserCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CategoryService {

    @Autowired
    private DefaultCategoryRepository defaultCategoryRepository;

    @Autowired
    private UserCategoryRepository userCategoryRepository;

    // Method to get all categories for a specific user
    public List<Category> getAllCategoriesForUser(User user) {
        List<DefaultCategory> defaultCategories = defaultCategoryRepository.findAll();
        List<UserCategory> userCategories = userCategoryRepository.findByUser(user);

        return Stream.concat(defaultCategories.stream(), userCategories.stream())
                     .collect(Collectors.toList());
    }

    // Default Category Methods
    public List<DefaultCategory> getAllDefaultCategories() {
        return defaultCategoryRepository.findAll();
    }

    public DefaultCategory getDefaultCategoryById(Long id) {
        return defaultCategoryRepository.findById(id).orElse(null);
    }

    public DefaultCategory saveDefaultCategory(DefaultCategory category) {
        return defaultCategoryRepository.save(category);
    }

    public void deleteDefaultCategory(Long id) {
        defaultCategoryRepository.deleteById(id);
    }

    // User Category Methods
    public List<UserCategory> getUserCategoriesByUser(User user) {
        return userCategoryRepository.findByUser(user);
    }

    public UserCategory getUserCategoryById(Long id) {
        return userCategoryRepository.findById(id).orElse(null);
    }

    public UserCategory saveUserCategory(UserCategory category) {
        return userCategoryRepository.save(category);
    }

    public void deleteUserCategory(Long id) {
        userCategoryRepository.deleteById(id);
    }
}
