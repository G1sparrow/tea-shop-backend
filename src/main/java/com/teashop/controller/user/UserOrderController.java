package com.teashop.controller.user;

import com.teashop.dto.request.CreateOrderRequest;
import com.teashop.dto.response.ApiResponse;
import com.teashop.dto.response.OrderResponse;
import com.teashop.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user/orders")
public class UserOrderController {

    @Autowired
    private UserOrderService orderService;

    @PostMapping
    public ApiResponse<OrderResponse> createOrder(
            @RequestParam Long userId,
            @Valid @RequestBody CreateOrderRequest request) {
        OrderResponse order = orderService.createOrder(userId, request);
        return ApiResponse.success("订单创建成功", order);
    }

    @GetMapping("/{id}")
    public ApiResponse<OrderResponse> getOrderById(@PathVariable Long id) {
        OrderResponse order = orderService.getOrderById(id);
        return ApiResponse.success(order);
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<Page<OrderResponse>> getUserOrders(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<OrderResponse> orders = orderService.getUserOrders(userId, pageable);
        return ApiResponse.success(orders);
    }
}