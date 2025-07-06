package com.sowciamn.recsav.model.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Store {
    private Integer storeCd;
    private String storeNm;
    private String storeAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
