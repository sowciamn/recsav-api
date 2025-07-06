package com.sowciamn.recsav.model.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DepositAccount {
    private String depositAccountCd;
    private String depositAccountNm;
    private String depositUsage;
    private String investmentAccountFlg;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
