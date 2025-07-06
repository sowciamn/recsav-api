package com.sowciamn.recsav.model.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssetRequest {

    @NotNull(message = "資産年月は必須です")
    private LocalDate assetYearMonth;

    @NotNull(message = "預金口座コードは必須です")
    private String depositAccountCd;

    @NotNull(message = "資産金額は必須です")
    private BigDecimal assetAmount;

    private String assetRemarks;
}
