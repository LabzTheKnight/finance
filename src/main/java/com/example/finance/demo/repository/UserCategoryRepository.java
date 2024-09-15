package com.example.finance.demo.repository;

import com.example.finance.demo.model.User;
import com.example.finance.demo.model.UserCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {
    List<UserCategory> findByUser(User user);
    List<UserCategory> findByUserAndName(User user, String name);
}

