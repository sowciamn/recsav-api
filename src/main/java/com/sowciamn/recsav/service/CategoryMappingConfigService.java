package com.sowciamn.recsav.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sowciamn.recsav.model.entity.CategoryMappingConfig;
import com.sowciamn.recsav.repository.CategoryMappingConfigMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryMappingConfigService {

    private final CategoryMappingConfigMapper categoryMappingConfigMapper;

    public List<CategoryMappingConfig> findAll() {
        return categoryMappingConfigMapper.findAll();
    }

    public CategoryMappingConfig findById(Integer categoryMappingConfigSeq) {
        return categoryMappingConfigMapper.findById(categoryMappingConfigSeq);
    }

    @Transactional
    public CategoryMappingConfig create(CategoryMappingConfig categoryMappingConfig) {
        categoryMappingConfigMapper.insert(categoryMappingConfig);
        return categoryMappingConfig;
    }

    @Transactional
    public CategoryMappingConfig update(Integer categoryMappingConfigSeq, CategoryMappingConfig categoryMappingConfig) {
        categoryMappingConfig.setCategoryMappingConfigSeq(categoryMappingConfigSeq);
        categoryMappingConfigMapper.update(categoryMappingConfig);
        return categoryMappingConfig;
    }

    @Transactional
    public void delete(Integer categoryMappingConfigSeq) {
        categoryMappingConfigMapper.delete(categoryMappingConfigSeq);
    }
}
