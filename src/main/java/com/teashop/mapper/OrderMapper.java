package com.teashop.mapper;

import com.teashop.common.entity.Order;
import com.teashop.common.entity.OrderStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {

    Order findById(@Param("id") Long id);

    Order findByOrderNo(@Param("orderNo") String orderNo);

    List<Order> findByUser(@Param("userId") Long userId);

    List<Order> findByUserWithPage(@Param("userId") Long userId, @Param("offset") int offset, @Param("limit") int limit);

    List<Order> findByStatus(@Param("status") OrderStatus status);

    List<Order> findAll();

    int insert(Order order);

    int update(Order order);

    int updateStatus(@Param("id") Long id, @Param("status") OrderStatus status);

    int deleteById(@Param("id") Long id);

    int countByUser(@Param("userId") Long userId);

    Long countAll();
}
