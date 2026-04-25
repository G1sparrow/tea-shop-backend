package com.teashop.service.impl;

import com.teashop.dto.request.CreateOrderRequest;
import com.teashop.dto.response.OrderItemResponse;
import com.teashop.dto.response.OrderResponse;
import com.teashop.common.entity.*;
import com.teashop.common.exception.BusinessException;
import com.teashop.common.exception.ResourceNotFoundException;
import com.teashop.mapper.*;
import com.teashop.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserOrderServiceImpl implements UserOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CartMapper cartMapper;

    @Override
    public OrderResponse createOrder(Long userId, CreateOrderRequest request) {
        // 获取用户
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("用户不存在");
        }

        // 验证商品和库存
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CreateOrderRequest.OrderItemRequest itemReq : request.getItems()) {
            Product product = productMapper.findById(itemReq.getProductId());
            if (product == null) {
                throw new ResourceNotFoundException("商品不存在: " + itemReq.getProductId());
            }

            if (product.getStock() < itemReq.getQuantity()) {
                throw new BusinessException("商品库存不足: " + product.getName());
            }

            // 计算小计
            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            totalAmount = totalAmount.add(subtotal);

            // 创建订单项
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemReq.getQuantity());
            orderItem.setPrice(product.getPrice()); // 保存下单时的价格
            orderItems.add(orderItem);
        }

        // 计算运费（简单逻辑，实际可根据重量、地区等计算）
        BigDecimal freight = totalAmount.compareTo(BigDecimal.valueOf(99)) >= 0 ? 
                             BigDecimal.ZERO : BigDecimal.valueOf(8);

        // 创建订单
        Order order = new Order();
        order.setOrderNo(generateOrderNo());
        order.setUser(user);
        order.setTotalAmount(totalAmount.add(freight));
        order.setShippingAddress(request.getShippingAddress());
        order.setShippingName(request.getShippingName());
        order.setShippingPhone(request.getShippingPhone());
        order.setFreight(freight);
        order.setStatus(OrderStatus.PENDING_PAYMENT); // 默认待支付状态

        // 保存订单
        int result = orderMapper.insert(order);
        if (result <= 0) {
            throw new BusinessException("创建订单失败");
        }

        // 设置订单项的订单关联并保存
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order); // 使用setOrder方法设置订单关联
            int itemResult = orderItemMapper.insert(orderItem);
            if (itemResult <= 0) {
                throw new BusinessException("创建订单项失败");
            }

            // 减少商品库存
            Product product = orderItem.getProduct();
            product.setStock(product.getStock() - orderItem.getQuantity());
            int updateResult = productMapper.update(product);
            if (updateResult <= 0) {
                throw new BusinessException("更新商品库存失败");
            }
        }

        // 如果是从购物车创建订单，清除购物车
        cartMapper.deleteByUser(user.getId());

        return convertToResponse(order, orderItems);
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        Order order = orderMapper.findById(orderId);
        if (order == null) {
            throw new ResourceNotFoundException("订单不存在");
        }

        List<OrderItem> orderItems = orderItemMapper.findByOrderId(orderId);
        return convertToResponse(order, orderItems);
    }

    @Override
    public Page<OrderResponse> getUserOrders(Long userId, Pageable pageable) {
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("用户不存在");
        }

        int offset = (int) pageable.getOffset();
        int limit = pageable.getPageSize();
        List<Order> orders = orderMapper.findByUserWithPage(userId, offset, limit);
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order order : orders) {
            List<OrderItem> orderItems = orderItemMapper.findByOrderId(order.getId());
            orderResponses.add(convertToResponse(order, orderItems));
        }
        
        int totalCount = orderMapper.countByUser(userId);
        return new org.springframework.data.domain.PageImpl<>(orderResponses, pageable, totalCount);
    }

    private String generateOrderNo() {
        // 生成订单号：年月日时分秒 + 随机数
        String timestamp = String.valueOf(System.currentTimeMillis());
        String randomPart = String.valueOf((int)(Math.random() * 10000));
        return "TEA" + timestamp.substring(timestamp.length() - 8) + randomPart;
    }

    private OrderResponse convertToResponse(Order order, List<OrderItem> orderItems) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setOrderNo(order.getOrderNo());
        response.setUserId(order.getUser().getId());
        
        // 获取用户信息
        User user = userMapper.findById(order.getUser().getId());
        response.setUsername(user != null ? user.getUsername() : "未知用户");
        
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(order.getStatus());
        response.setShippingAddress(order.getShippingAddress());
        response.setShippingPhone(order.getShippingPhone());
        response.setShippingName(order.getShippingName());
        response.setFreight(order.getFreight());
        response.setCreateTime(order.getCreateTime());
        response.setUpdateTime(order.getUpdateTime());

        // 转换订单项
        List<OrderItemResponse> itemResponses = orderItems.stream().map(item -> {
            OrderItemResponse itemResp = new OrderItemResponse();
            itemResp.setId(item.getId());
            itemResp.setOrderId(item.getOrder().getId());
            itemResp.setProductId(item.getProduct().getId());
            
            // 获取商品信息
            Product product = productMapper.findById(item.getProduct().getId());
            itemResp.setProductName(product != null ? product.getName() : "未知商品");
            itemResp.setProductDescription(product != null ? product.getDescription() : "");
            
            itemResp.setQuantity(item.getQuantity());
            itemResp.setPrice(item.getPrice());
            itemResp.setSubtotal(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            return itemResp;
        }).collect(Collectors.toList());

        response.setItems(itemResponses);
        return response;
    }
}