package com.sowciamn.recsav.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sowciamn.recsav.model.entity.CategoryMappingConfig;

@Mapper
public interface CategoryMappingConfigMapper {
    List<CategoryMappingConfig> findAll();
    CategoryMappingConfig findById(Integer categoryMappingConfigSeq);
    void insert(CategoryMappingConfig categoryMappingConfig);
    void update(CategoryMappingConfig categoryMappingConfig);
    void delete(Integer categoryMappingConfigSeq);
}
