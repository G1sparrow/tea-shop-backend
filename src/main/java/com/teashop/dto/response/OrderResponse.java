package com.teashop.dto.response;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.teashop.common.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private String orderNo;
    private Long userId;
    private String username;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private String shippingAddress;
    private String shippingPhone;
    private String shippingName;
    private BigDecimal freight;
    private List<OrderItemResponse> items;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}