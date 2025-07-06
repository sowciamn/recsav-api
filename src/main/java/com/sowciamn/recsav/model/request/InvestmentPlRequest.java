package com.sowciamn.recsav.model.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InvestmentPlRequest {

    @NotNull(message = "投資損益年月は必須です")
    private LocalDate investmentPlYearMonth;

    @NotNull(message = "預金口座コードは必須です")
    private String depositAccountCd;

    @NotNull(message = "投資損益金額は必須です")
    private BigDecimal investmentPlAmount;

    private String investmentPlRemarks;
}
