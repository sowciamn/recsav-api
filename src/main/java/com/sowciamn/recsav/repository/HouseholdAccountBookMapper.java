package com.sowciamn.recsav.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sowciamn.recsav.model.entity.HouseholdAccountBook;

@Mapper
public interface HouseholdAccountBookMapper {
    List<HouseholdAccountBook> findAll();
    Optional<HouseholdAccountBook> findById(@Param("habSeq") Long habSeq);
    int insert(HouseholdAccountBook householdAccountBook);
    int update(HouseholdAccountBook householdAccountBook);
    int deleteById(@Param("habSeq") Long habSeq);
}
