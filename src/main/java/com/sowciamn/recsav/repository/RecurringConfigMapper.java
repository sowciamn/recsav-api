package com.sowciamn.recsav.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sowciamn.recsav.model.entity.RecurringConfig;

@Mapper
public interface RecurringConfigMapper {
    List<RecurringConfig> findAll();
    RecurringConfig findById(Integer recurringConfigSeq);
    void insert(RecurringConfig recurringConfig);
    void update(RecurringConfig recurringConfig);
    void delete(Integer recurringConfigSeq);
}
