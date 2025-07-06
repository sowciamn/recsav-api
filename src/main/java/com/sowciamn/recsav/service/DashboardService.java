package com.sowciamn.recsav.service;

import java.time.LocalDate;
import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sowciamn.recsav.model.response.DashboardResponse;
import com.sowciamn.recsav.repository.DashboardMapper;

@Service
public class DashboardService {

    private final DashboardMapper dashboardMapper;

    @Autowired
    public DashboardService(DashboardMapper dashboardMapper) {
        this.dashboardMapper = dashboardMapper;
    }

    public DashboardResponse getDashboardData(YearMonth yearMonth) {
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        DashboardResponse response = new DashboardResponse();
        response.setSummary(dashboardMapper.findSummaryByMonth(startDate, endDate));
        response.setCategoryExpenses(dashboardMapper.findCategoryExpensesByMonth(startDate, endDate));

        return response;
    }
}
