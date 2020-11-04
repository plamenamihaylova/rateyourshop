package com.ratingapp.service.impl;

import com.ratingapp.exception.NotFoundEntityException;
import com.ratingapp.model.Category;
import com.ratingapp.repository.CategoryRepository;
import com.ratingapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundEntityException(String.format("Category with ID %d does not exist.", id)));
    }

    @Override
    public Category findByName(String name) {
        Category result = categoryRepository.findByNameIgnoreCase(name);
        if(result == null){
            throw new NotFoundEntityException(String.format("Category with name %s does not exist.", name)) ;
        }
        return result;
    }

    @Override
    public Category createCategory(Category category) {
        categoryRepository.saveAndFlush(category);
        return category;
    }

    @Override
    public Category deleteCategory(Long id) throws NotFoundEntityException {
        Category result = findById(id);
        categoryRepository.delete(result);
        return result;
    }

    @Override
    public Long count() {
        return categoryRepository.count();
    }
}
