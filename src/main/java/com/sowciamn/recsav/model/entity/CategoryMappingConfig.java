package com.sowciamn.recsav.model.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CategoryMappingConfig {
    private Integer categoryMappingConfigSeq;
    private String mappingKeyNm;
    private Integer categoryCd;
    private String linkingExcludedFlg;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
