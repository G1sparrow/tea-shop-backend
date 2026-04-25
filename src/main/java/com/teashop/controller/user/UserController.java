package com.teashop.controller.user;

import com.teashop.dto.request.LoginRequest;
import com.teashop.dto.request.RegisterRequest;
import com.teashop.dto.response.ApiResponse;
import com.teashop.dto.response.LoginResponse;
import com.teashop.dto.response.UserResponse;
import com.teashop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        UserResponse response = userService.register(request);
        return ApiResponse.success(response);
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);
        return ApiResponse.success(response);
    }

    @GetMapping("/profile")
    public ApiResponse<UserResponse> getProfile(@RequestParam Long userId) {
        UserResponse response = userService.findById(userId);
        return ApiResponse.success(response);
    }

    @PutMapping("/profile")
    public ApiResponse<UserResponse> updateProfile(
            @RequestParam Long userId,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String address) {
        UserResponse response = userService.updateProfile(userId, phone, email, address);
        return ApiResponse.success(response);
    }

    @GetMapping("/me")
    public ApiResponse<UserResponse> getCurrentUser(@RequestHeader("Authorization") String authorization) {
        // 从Authorization头中提取token
        String token = authorization.substring(7); // 去掉"Bearer "前缀
        UserResponse response = userService.getCurrentUser(token);
        return ApiResponse.success(response);
    }
}