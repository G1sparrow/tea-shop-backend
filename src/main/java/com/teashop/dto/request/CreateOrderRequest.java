package com.teashop.dto.request;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateOrderRequest {
    @NotEmpty(message = "订单项不能为空")
    private List<OrderItemRequest> items;

    @NotNull(message = "收货地址不能为空")
    private String shippingAddress;

    @NotNull(message = "收货人姓名不能为空")
    private String shippingName;

    @NotNull(message = "收货人电话不能为空")
    private String shippingPhone;

    private String remark; // 订单备注

    @Data
    public static class OrderItemRequest {
        private Long productId;
        private Integer quantity;
    }
}