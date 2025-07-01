package com.sowciamn.recsav.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class CategoryRequest {

    @NotBlank(message = "カテゴリ名は必須です。")
    @Size(max = 100, message = "カテゴリ名は100文字以内で入力してください。")
    private String categoryNm;

    @NotBlank(message = "カテゴリ種別は必須です。")
    private String categoryType; // "1":収入, "2":支出

    @NotNull(message = "表示順は必須です。")
    private Integer displayOrder;
}