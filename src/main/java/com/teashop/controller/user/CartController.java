package com.teashop.controller.user;

import com.teashop.dto.request.AddToCartRequest;
import com.teashop.dto.response.ApiResponse;
import com.teashop.common.entity.Cart;
import com.teashop.service.UserCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private UserCartService cartService;

    @GetMapping
    public ApiResponse<List<Cart>> getUserCart(@RequestParam Long userId) {
        List<Cart> cartItems = cartService.getUserCart(userId);
        return ApiResponse.success(cartItems);
    }

    @PostMapping
    public ApiResponse<Cart> addToCart(@RequestParam Long userId, @Valid @RequestBody AddToCartRequest request) {
        Cart cartItem = cartService.addToCart(userId, request);
        return ApiResponse.success("添加到购物车成功", cartItem);
    }

    @PutMapping("/{productId}")
    public ApiResponse<Cart> updateCartItem(
            @RequestParam Long userId,
            @PathVariable Long productId,
            @RequestParam Integer quantity) {
        Cart cartItem = cartService.updateCartItemQuantity(userId, productId, quantity);
        return ApiResponse.success("更新购物车成功", cartItem);
    }

    @DeleteMapping("/{productId}")
    public ApiResponse<Void> removeFromCart(
            @RequestParam Long userId,
            @PathVariable Long productId) {
        cartService.removeFromCart(userId, productId);
        return ApiResponse.success("从购物车删除商品成功", null);
    }

    @DeleteMapping("/clear")
    public ApiResponse<Void> clearCart(@RequestParam Long userId) {
        cartService.clearCart(userId);
        return ApiResponse.success("清空购物车成功", null);
    }
}