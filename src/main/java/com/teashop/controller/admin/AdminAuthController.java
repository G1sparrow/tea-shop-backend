package com.teashop.controller.admin;

import com.teashop.dto.request.AdminLoginRequest;
import com.teashop.dto.response.AdminLoginResponse;
import com.teashop.dto.response.ApiResponse;
import com.teashop.common.entity.Admin;
import com.teashop.common.exception.BusinessException;
import com.teashop.common.util.JwtUtil;
import com.teashop.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminAuthController {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ApiResponse<AdminLoginResponse> login(@Valid @RequestBody AdminLoginRequest request) {
        Admin admin = adminMapper.findByUsername(request.getUsername());
        if (admin == null) {
            throw new BusinessException("管理员用户名或密码错误");
        }

        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new BusinessException("管理员用户名或密码错误");
        }

        String token = jwtUtil.generateToken(admin.getId(), admin.getUsername());
        return ApiResponse.success(new AdminLoginResponse(admin, token));
    }
}
