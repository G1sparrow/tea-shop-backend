package com.teashop.service.impl;

import com.teashop.service.AdminService;
import com.teashop.common.entity.Admin;
import com.teashop.common.exception.ResourceNotFoundException;
import com.teashop.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin findById(Long id) {
        Admin admin = adminMapper.findById(id);
        if (admin == null) {
            throw new ResourceNotFoundException("管理员不存在");
        }
        return admin;
    }

    @Override
    public Admin findByUsername(String username) {
        return adminMapper.findByUsername(username);
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminMapper.findAll();
    }

    @Override
    public Admin createAdmin(Admin admin) {
        int result = adminMapper.insert(admin);
        if (result <= 0) {
            throw new RuntimeException("创建管理员失败");
        }
        return admin;
    }

    @Override
    public Admin updateAdmin(Long id, String phone, String email, String name) {
        Admin admin = adminMapper.findById(id);
        if (admin == null) {
            throw new ResourceNotFoundException("管理员不存在");
        }

        if (phone != null) admin.setPhone(phone);
        if (email != null) admin.setEmail(email);
        if (name != null) admin.setName(name);

        int result = adminMapper.update(admin);
        if (result <= 0) {
            throw new RuntimeException("更新管理员失败");
        }
        return admin;
    }

    @Override
    public void deleteAdmin(Long id) {
        Admin admin = adminMapper.findById(id);
        if (admin == null) {
            throw new ResourceNotFoundException("管理员不存在");
        }

        int result = adminMapper.deleteById(id);
        if (result <= 0) {
            throw new RuntimeException("删除管理员失败");
        }
    }

    @Override
    public Long getTotalAdminCount() {
        List<Admin> admins = adminMapper.findAll();
        return (long) admins.size();
    }
}
