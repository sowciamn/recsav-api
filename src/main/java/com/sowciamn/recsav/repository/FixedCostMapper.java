package com.sowciamn.recsav.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sowciamn.recsav.model.entity.FixedCost;

@Mapper
public interface FixedCostMapper {
    List<FixedCost> findAll();
    Optional<FixedCost> findById(@Param("fixedCostSeq") Long fixedCostSeq);
    int insert(FixedCost fixedCost);
    int update(FixedCost fixedCost);
    int deleteById(@Param("fixedCostSeq") Long fixedCostSeq);
}
