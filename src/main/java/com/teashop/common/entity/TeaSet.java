package com.teashop.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TeaSet implements Serializable {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String material;
    private String category;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}