package com.sowciamn.recsav.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class HouseholdAccountBook {
    private Long habSeq;
    private LocalDate actualDate;
    private Integer categoryCd;
    private Integer storeCd;
    private BigDecimal amount;
    private String remarks;
    private Integer linkingDataType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
