package com.teashop.service;

import com.teashop.common.entity.Admin;
import com.teashop.dto.response.UserResponse;

import java.util.List;

public interface AdminService {
    Admin findById(Long id);
    Admin findByUsername(String username);
    List<Admin> getAllAdmins();
    Admin createAdmin(Admin admin);
    Admin updateAdmin(Long id, String phone, String email, String name);
    void deleteAdmin(Long id);
    Long getTotalAdminCount();
}
