package com.sowciamn.recsav.test.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.sowciamn.recsav.model.response.CategoryExpense;
import com.sowciamn.recsav.model.response.DashboardResponse;
import com.sowciamn.recsav.model.response.DashboardSummary;
import com.sowciamn.recsav.service.DashboardService;
import com.sowciamn.recsav.controller.DashboardController;

@WebMvcTest(DashboardController.class)
public class DashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DashboardService dashboardService;

    private DashboardResponse dashboardResponse;

    @BeforeEach
    void setUp() {
        DashboardSummary summary = new DashboardSummary();
        summary.setDepositTotal(new BigDecimal("300000"));
        summary.setExpenseTotal(new BigDecimal("150000"));
        summary.setBalance(new BigDecimal("150000"));

        CategoryExpense categoryExpense1 = new CategoryExpense();
        categoryExpense1.setCategoryNm("食費");
        categoryExpense1.setAmount(new BigDecimal("50000"));

        CategoryExpense categoryExpense2 = new CategoryExpense();
        categoryExpense2.setCategoryNm("交通費");
        categoryExpense2.setAmount(new BigDecimal("20000"));

        dashboardResponse = new DashboardResponse();
        dashboardResponse.setSummary(summary);
        dashboardResponse.setCategoryExpenses(Arrays.asList(categoryExpense1, categoryExpense2));
    }

    @Test
    void getDashboardData_shouldReturnDashboardResponse() throws Exception {
        when(dashboardService.getDashboardData(any(YearMonth.class))).thenReturn(dashboardResponse);

        mockMvc.perform(get("/api/dashboard")
                .param("yearMonth", "2024-06"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.summary.depositTotal").value(dashboardResponse.getSummary().getDepositTotal()))
                .andExpect(jsonPath("$.summary.expenseTotal").value(dashboardResponse.getSummary().getExpenseTotal()))
                .andExpect(jsonPath("$.categoryExpenses[0].categoryNm").value(dashboardResponse.getCategoryExpenses().get(0).getCategoryNm()));
    }
}
