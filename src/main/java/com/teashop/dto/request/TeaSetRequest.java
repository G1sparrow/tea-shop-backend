package com.teashop.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TeaSetRequest {
    @NotBlank(message = "茶器名称不能为空")
    private String name;

    @NotBlank(message = "茶器描述不能为空")
    private String description;

    @NotNull(message = "茶器价格不能为空")
    @Positive(message = "茶器价格必须大于0")
    private BigDecimal price;

    @NotBlank(message = "茶器材质不能为空")
    private String material;

    @NotBlank(message = "茶器分类不能为空")
    private String category;

    private Boolean status = true;
}