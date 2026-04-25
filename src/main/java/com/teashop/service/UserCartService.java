package com.teashop.service;

import com.teashop.dto.request.AddToCartRequest;
import com.teashop.common.entity.Cart;
import com.teashop.common.exception.BusinessException;
import com.teashop.common.exception.ResourceNotFoundException;

import java.util.List;

public interface UserCartService {
    Cart addToCart(Long userId, AddToCartRequest request) throws ResourceNotFoundException, BusinessException;
    
    List<Cart> getUserCart(Long userId) throws ResourceNotFoundException;
    
    Cart updateCartItemQuantity(Long userId, Long productId, Integer quantity) throws ResourceNotFoundException, BusinessException;
    
    void removeFromCart(Long userId, Long productId) throws ResourceNotFoundException;
    
    void clearCart(Long userId) throws ResourceNotFoundException;
}
