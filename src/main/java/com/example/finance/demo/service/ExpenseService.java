package com.example.finance.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.finance.demo.model.Expense;
import com.example.finance.demo.model.User;
import com.example.finance.demo.model.UserCategory;
import com.example.finance.demo.model.Category;
import com.example.finance.demo.repository.ExpenseRepository;
import com.example.finance.demo.repository.UserRepository;
import com.example.finance.demo.repository.DefaultCategoryRepository;
import com.example.finance.demo.repository.UserCategoryRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DefaultCategoryRepository defaultCategoryRepository;

    @Autowired
    private UserCategoryRepository userCategoryRepository;

    public List<Expense> getExpensesForUser(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return expenseRepository.findByUser(user);
    }

    public void addExpense(String name, Double amount, String date, String username, String categoryName) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Category category = getCategoryForUser(user, categoryName);

        Expense expense = new Expense();
        expense.setName(name);
        expense.setAmount(amount);
        expense.setDate(LocalDate.parse(date)); 
        expense.setUser(user);
        expense.setCategory(category);
        
        expenseRepository.save(expense);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public Optional<Expense> findExpenseById(Long id) {
        return expenseRepository.findById(id);
    }

    public void updateExpense(Long expenseId, String name, Double amount, String date, String username, String categoryName) {
        Expense expense = expenseRepository.findById(expenseId)
            .orElseThrow(() -> new RuntimeException("Expense not found"));

        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = getCategoryForUser(user, categoryName);

        if (expense.getUser().equals(user)) {
            expense.setName(name);
            expense.setAmount(amount);
            expense.setDate(LocalDate.parse(date)); 
            expense.setCategory(category);
            expenseRepository.save(expense);
        } else {
            throw new RuntimeException("Unauthorized to update this expense");
        }
    }

    public List<Expense> getExpensesByCategory(String categoryName, User user) {
        Category category = getCategoryForUser(user, categoryName);
        return expenseRepository.findByUserAndCategory(user, category);
    }

    private Category getCategoryForUser(User user, String categoryName) {
        // First, look for user-specific categories
        List<UserCategory> userCategories = userCategoryRepository.findByUser(user);

        // Check user-specific categories
        Optional<UserCategory> userCategory = userCategories.stream()
            .filter(c -> c.getName().equals(categoryName))
            .findFirst();

        if (userCategory.isPresent()) {
            return userCategory.get();
        } else {
            // Fallback to default categories
            return defaultCategoryRepository.findByName(categoryName)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        }
    }
}
