package com.ratingapp.service;

import com.ratingapp.exception.InvalidEntityDataException;
import com.ratingapp.exception.NotFoundEntityException;
import com.ratingapp.model.Category;
import com.ratingapp.model.Shop;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface CategoryService {

    List<Category> findAllCategories();
    Category findById(Long id) throws NotFoundEntityException;
    Category findByName(String name) throws NotFoundEntityException;
    Category createCategory(Category category) throws DataIntegrityViolationException, InvalidEntityDataException;
    Category deleteCategory(Long id) throws NotFoundEntityException;
    Long count();
}