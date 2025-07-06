package com.sowciamn.recsav.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class InvestmentPl {
    private LocalDate investmentPlYearMonth;
    private String depositAccountCd;
    private BigDecimal investmentPlAmount;
    private String investmentPlRemarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
