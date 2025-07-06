package com.sowciamn.recsav.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DepositAccountRequest {

    @NotBlank(message = "預金口座コードは必須です")
    @Size(max = 3, message = "預金口座コードは3文字で入力してください")
    private String depositAccountCd;

    @NotBlank(message = "預金口座名は必須です")
    @Size(max = 100, message = "預金口座名は100文字以内で入力してください")
    private String depositAccountNm;

    private String depositUsage;

    @Size(max = 1, message = "投資口座フラグは1文字で入力してください")
    private String investmentAccountFlg;
}
