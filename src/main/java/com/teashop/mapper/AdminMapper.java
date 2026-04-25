package com.teashop.mapper;

import com.teashop.common.entity.Admin;

import java.util.List;

public interface AdminMapper {
    Admin findById(Long id);
    Admin findByUsername(String name);
    List<Admin> findAll();
    int insert(Admin admin);
    int update(Admin admin);
    int deleteById(Long id);
}
