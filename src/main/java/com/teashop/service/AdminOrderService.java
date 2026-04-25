package com.teashop.service;

import com.teashop.dto.response.OrderResponse;
import com.teashop.common.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminOrderService {
    Page<OrderResponse> getAllOrders(Pageable pageable);
    
    OrderResponse updateOrderStatus(Long orderId, OrderStatus status);
    
    OrderResponse getOrderById(Long orderId);
    
    Long getTotalOrderCount();
    
    Double getTodaySales();
    
    List<Object> getRecentOrders(int limit);
}