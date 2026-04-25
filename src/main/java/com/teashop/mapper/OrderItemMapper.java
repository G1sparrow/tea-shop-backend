package com.teashop.mapper;

import com.teashop.common.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderItemMapper {

    OrderItem findById(@Param("id") Long id);

    List<OrderItem> findByOrderId(@Param("orderId") Long orderId);

    List<OrderItem> findByOrderIdWithDetails(@Param("orderId") Long orderId);

    List<OrderItem> findByProductId(@Param("productId") Long productId);

    int insert(OrderItem orderItem);

    int update(OrderItem orderItem);

    int deleteById(@Param("id") Long id);

    int deleteByOrderId(@Param("orderId") Long orderId);
}
