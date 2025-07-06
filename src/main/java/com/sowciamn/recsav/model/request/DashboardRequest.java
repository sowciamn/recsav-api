package com.sowciamn.recsav.model.request;

import java.time.YearMonth;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DashboardRequest {

    @NotNull(message = "年月は必須です")
    @DateTimeFormat(pattern = "yyyy-MM")
    private YearMonth yearMonth;
}
