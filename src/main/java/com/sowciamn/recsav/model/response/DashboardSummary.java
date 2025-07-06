package com.sowciamn.recsav.model.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DashboardSummary {
    private BigDecimal depositTotal;
    private BigDecimal expenseTotal;
    private BigDecimal balance;
}
