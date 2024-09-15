package com.example.finance.demo.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.finance.demo.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> { 
    Optional<Category> findByName(String name);
}
