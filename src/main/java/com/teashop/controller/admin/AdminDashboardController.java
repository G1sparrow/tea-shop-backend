package com.teashop.controller.admin;

import com.teashop.dto.response.ApiResponse;
import com.teashop.service.AdminOrderService;
import com.teashop.service.AdminProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/dashboard")
public class AdminDashboardController {

    @Autowired
    private AdminProductService productService;
    
    @Autowired
    private AdminOrderService orderService;

    @GetMapping("/stats")
    public ApiResponse<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        // 商品总数
        stats.put("productCount", productService.getTotalProductCount());
        
        // 订单总数
        stats.put("orderCount", orderService.getTotalOrderCount());
        
        // 今日销售额
        stats.put("todaySales", orderService.getTodaySales());
        
        return ApiResponse.success(stats);
    }

    @GetMapping("/recent-orders")
    public ApiResponse<Object> getRecentOrders(@RequestParam(defaultValue = "5") int limit) {
        // 获取最近的订单
        return ApiResponse.success(orderService.getRecentOrders(limit));
    }
}