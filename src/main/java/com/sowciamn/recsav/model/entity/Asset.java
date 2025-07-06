package com.sowciamn.recsav.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Asset {
    private LocalDate assetYearMonth;
    private String depositAccountCd;
    private BigDecimal assetAmount;
    private String assetRemarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
