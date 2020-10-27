package com.ratingapp.service;

import com.ratingapp.model.Category;
import com.ratingapp.model.Shop;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();
    Category findById(Long id);
    Category findByName(String name);
    Category createCategory(Category category);
    Long count();
}
