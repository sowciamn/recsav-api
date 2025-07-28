package com.sowciamn.recsav.model.request;

import lombok.Data;

@Data
public class CategoryMappingConfigRequest {
    private String mappingKeyNm;
    private Integer categoryCd;
    private String linkingExcludedFlg;
}
