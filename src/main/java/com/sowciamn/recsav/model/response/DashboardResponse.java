package com.sowciamn.recsav.model.response;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class DashboardResponse {
    private DashboardSummary summary;
    private List<CategoryExpense> categoryExpenses;
}
