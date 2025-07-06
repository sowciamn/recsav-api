package com.sowciamn.recsav.model.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CategoryExpense {
    private String categoryNm;
    private BigDecimal amount;
}
