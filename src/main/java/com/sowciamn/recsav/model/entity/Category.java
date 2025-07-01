package com.sowciamn.recsav.model.entity;

import lombok.Data;

@Data
public class Category {
    private Integer categoryCd;
    private String categoryNm;
    private String categoryType;
    private Integer displayOrder;
}