package com.example.finance.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.finance.demo.model.Expense;
import com.example.finance.demo.model.User;
import com.example.finance.demo.service.ExpenseService;
import com.example.finance.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Expense>> getExpensesForUser(@PathVariable String username) {
        List<Expense> expenses = expenseService.getExpensesForUser(username);
        return ResponseEntity.ok(expenses);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addExpense(
            @RequestParam String name,
            @RequestParam Double amount,
            @RequestParam String date,
            @RequestParam String username,
            @RequestParam String categoryName) {
        try {
            expenseService.addExpense(name, amount, date, username, categoryName);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<Expense> findExpenseById(@PathVariable Long id) {
        Optional<Expense> expense = expenseService.findExpenseById(id);
        return expense.map(ResponseEntity::ok)
                      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateExpense(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam Double amount,
            @RequestParam String date,
            @RequestParam String username,
            @RequestParam String categoryName) {
        try {
            expenseService.updateExpense(id, name, amount, date, username, categoryName);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // Unauthorized to update
        }
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<Expense>> getExpensesByCategory(
            @PathVariable String categoryName,
            @RequestParam String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        List<Expense> expenses = expenseService.getExpensesByCategory(categoryName, user);
        return ResponseEntity.ok(expenses);
    }
}
