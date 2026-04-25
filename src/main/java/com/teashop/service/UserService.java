package com.teashop.service;

import com.teashop.dto.request.LoginRequest;
import com.teashop.dto.request.RegisterRequest;
import com.teashop.dto.response.LoginResponse;
import com.teashop.dto.response.UserResponse;
import com.teashop.common.exception.BusinessException;
import com.teashop.common.exception.ResourceNotFoundException;

public interface UserService {
    UserResponse register(RegisterRequest request) throws BusinessException;
    
    LoginResponse login(LoginRequest request) throws BusinessException;
    
    UserResponse findById(Long id) throws ResourceNotFoundException;
    
    UserResponse updateProfile(Long id, String phone, String email, String address) throws ResourceNotFoundException;

    Long getTotalUserCount();
    
    UserResponse getCurrentUser(String token) throws ResourceNotFoundException;
}