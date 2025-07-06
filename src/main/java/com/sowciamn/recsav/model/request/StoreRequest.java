package com.sowciamn.recsav.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StoreRequest {

    @NotBlank(message = "店舗名は必須です")
    @Size(max = 100, message = "店舗名は100文字以内で入力してください")
    private String storeNm;

    @Size(max = 255, message = "住所は255文字以内で入力してください")
    private String storeAddress;
}
