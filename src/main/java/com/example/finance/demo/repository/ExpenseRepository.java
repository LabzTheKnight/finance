package com.example.finance.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.finance.demo.model.Category;
import com.example.finance.demo.model.Expense;
import com.example.finance.demo.model.User;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);
    List<Expense> findByUserAndCategory(User user, Category category);
}
