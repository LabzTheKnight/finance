package com.example.finance.demo.repository;

import com.example.finance.demo.model.Category;
import com.example.finance.demo.model.DefaultCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DefaultCategoryRepository extends JpaRepository<DefaultCategory, Long> {
    Optional<Category> findByName(String name);
}
