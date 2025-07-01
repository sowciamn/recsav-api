package com.sowciamn.recsav.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sowciamn.recsav.exception.ResourceNotFoundException;
import com.sowciamn.recsav.model.entity.Category;
import com.sowciamn.recsav.model.request.CategoryRequest;
import com.sowciamn.recsav.repository.CategoryMapper;

@Service
public class CategoryService {

    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryService(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public List<Category> findAllCategories() {
        return categoryMapper.findAll();
    }

    @Transactional
    public Category createCategory(CategoryRequest request) {
        Category category = new Category();
        BeanUtils.copyProperties(request, category);
        categoryMapper.insert(category);
        return category;
    }

    @Transactional
    public Category updateCategory(Integer categoryCd, CategoryRequest request) {
        // 存在チェック
        Category category = categoryMapper.findById(categoryCd)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryCd));

        // リクエストの内容をコピーして更新
        BeanUtils.copyProperties(request, category);
        categoryMapper.update(category);
        return category;
    }

    @Transactional
    public void deleteCategory(Integer categoryCd) {
        // 存在チェック
        categoryMapper.findById(categoryCd)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryCd));
        categoryMapper.deleteById(categoryCd);
    }
}