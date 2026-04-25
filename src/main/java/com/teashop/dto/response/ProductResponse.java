package com.teashop.dto.response;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.teashop.common.entity.ProductCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private ProductCategory category;
    private Integer stock;
    private Boolean status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}