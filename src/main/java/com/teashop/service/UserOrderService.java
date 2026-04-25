package com.teashop.service;

import com.teashop.dto.request.CreateOrderRequest;
import com.teashop.dto.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserOrderService {
    OrderResponse createOrder(Long userId, CreateOrderRequest request);
    
    OrderResponse getOrderById(Long orderId);
    
    Page<OrderResponse> getUserOrders(Long userId, Pageable pageable);
}