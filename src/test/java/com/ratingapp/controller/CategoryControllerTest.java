package com.ratingapp.controller;

import com.ratingapp.enums.Enums;
import com.ratingapp.model.Category;
import com.ratingapp.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryControllerTest {

    @Autowired
    CategoryService categoryService;

    @Test
    void findAllCategories() {
        List<Category> allCategories = categoryService.findAllCategories();
        assertNotNull(allCategories);
    }

    @Test
    void createCategory() {
    }

    @Test
    void deleteCategory() {
    }
}