package com.sowciamn.recsav.model.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FixedCostRequest {

    @NotNull(message = "カテゴリコードは必須です")
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
}
