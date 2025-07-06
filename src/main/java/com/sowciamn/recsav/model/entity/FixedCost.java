package com.sowciamn.recsav.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FixedCost {
    private Long fixedCostSeq;
    private Integer categoryCd;
    private String fixedCostDetails;
    private Integer displayOrder;
    private String remarks;
    private BigDecimal january;
    private BigDecimal february;
    private BigDecimal march;
    private BigDecimal april;
    private BigDecimal may;
    private BigDecimal june;
    private BigDecimal july;
    private BigDecimal august;
    private BigDecimal september;
    private BigDecimal october;
    private BigDecimal november;
    private BigDecimal december;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
