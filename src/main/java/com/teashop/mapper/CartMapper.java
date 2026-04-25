package com.teashop.mapper;

import com.teashop.common.entity.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartMapper {

    Cart findById(@Param("id") Long id);

    List<Cart> findByUser(@Param("userId") Long userId);

    Cart findByUserAndProduct(@Param("userId") Long userId, @Param("productId") Long productId);

    int insert(Cart cart);

    int update(Cart cart);

    int deleteById(@Param("id") Long id);

    int deleteByUser(@Param("userId") Long userId);

    int deleteByUserAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);
}
