package com.teashop.dto.request;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

@Data
public class AddToCartRequest {
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    @Min(value = 1, message = "商品数量至少为1")
    private Integer quantity = 1;
}