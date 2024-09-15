package com.example.finance.demo;  // Ensure the package path is correct

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.finance.demo.model.DefaultCategory;
import com.example.finance.demo.repository.DefaultCategoryRepository;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDefaultCategories(DefaultCategoryRepository defaultCategoryRepository) {
        return args -> {
            if (defaultCategoryRepository.count() == 0) { // Only add if no categories exist
                DefaultCategory food = new DefaultCategory();
                food.setName("Food");
                defaultCategoryRepository.save(food);

                DefaultCategory transport = new DefaultCategory();
                transport.setName("Transport");
                defaultCategoryRepository.save(transport);

                DefaultCategory entertainment = new DefaultCategory();
                entertainment.setName("Entertainment");
                defaultCategoryRepository.save(entertainment);
                
                // Add more categories as needed
            }
        };
    }
}
