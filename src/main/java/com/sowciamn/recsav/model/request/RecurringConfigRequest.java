package com.sowciamn.recsav.model.request;

import lombok.Data;

@Data
public class RecurringConfigRequest {
    private String recurringNm;
    private String executionIntervalType;
    private Integer categoryCd;
    private Integer storeCd;
    private Integer amount;
    private String remarks;
    private Integer linkingDataType;
    private String activeFlg;
}
