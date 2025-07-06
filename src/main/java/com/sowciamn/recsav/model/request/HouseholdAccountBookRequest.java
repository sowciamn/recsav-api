package com.sowciamn.recsav.model.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HouseholdAccountBookRequest {

    @NotNull(message = "日付は必須です")
    private LocalDate actualDate;

    @NotNull(message = "カテゴリコードは必須です")
    private Integer categoryCd;

    private Integer storeCd;

    @NotNull(message = "金額は必須です")
    private BigDecimal amount;

    private String remarks;

    private Integer linkingDataType;
}
