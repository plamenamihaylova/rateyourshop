package com.ratingapp.controller;

import com.ratingapp.exception.ValidationErrorsException;
import com.ratingapp.model.Category;
import com.ratingapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> findAllCategories(){
        return categoryService.findAllCategories();
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category,
                                                    Errors errors,
                                                    HttpServletRequest request) {
        if (errors.hasErrors()) {
            throw new ValidationErrorsException(errors);
        }

        Category newCategory = categoryService.createCategory(category);
        return ResponseEntity
                .created(
                        UriComponentsBuilder.fromUriString(
                                request.getRequestURL().toString()).pathSegment("{id}")
                                .build(newCategory.getId()))
                .body(newCategory);

    }

    @DeleteMapping("{id}")
    public Category deleteCategory(@PathVariable Long id){
        return categoryService.deleteCategory(id);
    }
}
