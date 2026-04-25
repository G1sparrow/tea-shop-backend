package com.teashop.service.impl;

import com.teashop.dto.request.LoginRequest;
import com.teashop.dto.request.RegisterRequest;
import com.teashop.dto.response.LoginResponse;
import com.teashop.dto.response.UserResponse;
import com.teashop.common.entity.User;
import com.teashop.common.exception.BusinessException;
import com.teashop.common.exception.ResourceNotFoundException;
import com.teashop.common.util.JwtUtil;
import com.teashop.mapper.UserMapper;
import com.teashop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserResponse register(RegisterRequest request) throws BusinessException {
        // 检查用户名是否已存在
        if (userMapper.existsByUsername(request.getUsername())) {
            throw new BusinessException("用户名已存在");
        }

        // 创建新用户
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());

        int result = userMapper.insert(user);
        if (result <= 0) {
            throw new BusinessException("注册失败");
        }
        return convertToResponse(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) throws BusinessException {
        User user = userMapper.findByUsername(request.getUsername());
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        UserResponse userResponse = convertToResponse(user);
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        
        return new LoginResponse(userResponse, token);
    }

    @Override
    public UserResponse findById(Long id) throws ResourceNotFoundException {
        User user = userMapper.findById(id);
        if (user == null) {
            throw new ResourceNotFoundException("用户不存在");
        }
        return convertToResponse(user);
    }

    @Override
    public UserResponse updateProfile(Long id, String phone, String email, String address) throws ResourceNotFoundException {
        User user = userMapper.findById(id);
        if (user == null) {
            throw new ResourceNotFoundException("用户不存在");
        }

        user.setPhone(phone);
        user.setAddress(address);

        int result = userMapper.update(user);
        if (result <= 0) {
            throw new BusinessException("更新失败");
        }
        return convertToResponse(user);
    }

    private UserResponse convertToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setPhone(user.getPhone());
        response.setAddress(user.getAddress());
        response.setCreateTime(user.getCreateTime());
        response.setUpdateTime(user.getUpdateTime());
        return response;
    }
    
    @Override
    public Long getTotalUserCount() {
        List<User> users = userMapper.findAll();
        return (long) users.size();
    }

    @Override
    public UserResponse getCurrentUser(String token) throws ResourceNotFoundException {
        // 从token中解析用户ID
        Long userId = jwtUtil.extractUserId(token);
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("用户不存在");
        }
        return convertToResponse(user);
    }
}