package com.sowciamn.recsav.model.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RecurringConfig {
    private Integer recurringConfigSeq;
    private String recurringNm;
    private String executionIntervalType;
    private Integer categoryCd;
    private Integer storeCd;
    private Integer amount;
    private String remarks;
    private Integer linkingDataType;
    private String activeFlg;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
