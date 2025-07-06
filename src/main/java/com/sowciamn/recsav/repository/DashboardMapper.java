package com.sowciamn.recsav.repository;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sowciamn.recsav.model.response.CategoryExpense;
import com.sowciamn.recsav.model.response.DashboardSummary;

@Mapper
public interface DashboardMapper {
    DashboardSummary findSummaryByMonth(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    List<CategoryExpense> findCategoryExpensesByMonth(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
