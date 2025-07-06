package com.sowciamn.recsav.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sowciamn.recsav.model.entity.Store;

@Mapper
public interface StoreMapper {
    List<Store> findAll();
    Optional<Store> findById(@Param("storeCd") Integer storeCd);
    int insert(Store store);
    int update(Store store);
    int deleteById(@Param("storeCd") Integer storeCd);
}
