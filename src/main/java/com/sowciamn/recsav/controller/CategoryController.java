package com.sowciamn.recsav.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sowciamn.recsav.model.entity.Category;
import com.sowciamn.recsav.model.request.CategoryRequest;
import com.sowciamn.recsav.service.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.findAllCategories();
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryRequest request) {
        Category createdCategory = categoryService.createCategory(request);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryCd}")
    public ResponseEntity<Category> updateCategory(@PathVariable Integer categoryCd, @Valid @RequestBody CategoryRequest request) {
        Category updatedCategory = categoryService.updateCategory(categoryCd, request);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{categoryCd}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer categoryCd) {
        categoryService.deleteCategory(categoryCd);
        return ResponseEntity.noContent().build();
    }
}