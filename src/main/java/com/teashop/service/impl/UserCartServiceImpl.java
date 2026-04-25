package com.teashop.service.impl;

import com.teashop.dto.request.AddToCartRequest;
import com.teashop.common.entity.Cart;
import com.teashop.common.entity.Product;
import com.teashop.common.entity.User;
import com.teashop.common.exception.BusinessException;
import com.teashop.common.exception.ResourceNotFoundException;
import com.teashop.mapper.CartMapper;
import com.teashop.mapper.ProductMapper;
import com.teashop.mapper.UserMapper;
import com.teashop.service.UserCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserCartServiceImpl implements UserCartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Cart addToCart(Long userId, AddToCartRequest request) throws ResourceNotFoundException, BusinessException {
        // 获取用户和商品
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("用户不存在");
        }
        
        Product product = productMapper.findById(request.getProductId());
        if (product == null) {
            throw new ResourceNotFoundException("商品不存在");
        }

        // 检查库存
        if (product.getStock() < request.getQuantity()) {
            throw new BusinessException("商品库存不足");
        }

        // 检查购物车中是否已有该商品
        Cart existingCartItem = cartMapper.findByUserAndProduct(user.getId(), request.getProductId());

        if (existingCartItem != null) {
            // 如果已存在，则增加数量
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            int result = cartMapper.update(existingCartItem);
            if (result <= 0) {
                throw new BusinessException("更新购物车失败");
            }
            return existingCartItem;
        } else {
            // 否则创建新的购物车项
            Cart cartItem = new Cart();
            cartItem.setUser(user);
            cartItem.setProduct(productMapper.findById(request.getProductId()));
            cartItem.setQuantity(request.getQuantity());
            int result = cartMapper.insert(cartItem);
            if (result <= 0) {
                throw new BusinessException("添加到购物车失败");
            }
            return cartItem;
        }
    }

    @Override
    public List<Cart> getUserCart(Long userId) throws ResourceNotFoundException {
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("用户不存在");
        }
        return cartMapper.findByUser(userId);
    }

    @Override
    public Cart updateCartItemQuantity(Long userId, Long productId, Integer quantity) throws ResourceNotFoundException, BusinessException {
        if (quantity <= 0) {
            removeFromCart(userId, productId);
            return null;
        }

        User user = userMapper.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("用户不存在");
        }

        Product product = productMapper.findById(productId);
        if (product == null) {
            throw new ResourceNotFoundException("商品不存在");
        }

        // 检查库存
        if (product.getStock() < quantity) {
            throw new BusinessException("商品库存不足");
        }

        Cart cartItem = cartMapper.findByUserAndProduct(userId, productId);
        if (cartItem == null) {
            throw new ResourceNotFoundException("购物车中没有该商品");
        }

        cartItem.setQuantity(quantity);
        int result = cartMapper.update(cartItem);
        if (result <= 0) {
            throw new BusinessException("更新购物车失败");
        }
        return cartItem;
    }

    @Override
    public void removeFromCart(Long userId, Long productId) throws ResourceNotFoundException {
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("用户不存在");
        }

        cartMapper.deleteByUserAndProductId(user.getId(), productId);
    }

    @Override
    public void clearCart(Long userId) throws ResourceNotFoundException {
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("用户不存在");
        }

        cartMapper.deleteByUser(user.getId());
    }
}