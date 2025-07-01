package com.sowciamn.recsav.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sowciamn.recsav.model.entity.Category;

@Mapper
public interface CategoryMapper {
    List<Category> findAll();
    Optional<Category> findById(@Param("categoryCd") Integer categoryCd);
    int insert(Category category);
    int update(Category category);
    int deleteById(@Param("categoryCd") Integer categoryCd);
}