package com.teashop.controller.admin;

import com.teashop.dto.response.ApiResponse;
import com.teashop.common.entity.Admin;
import com.teashop.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping
    public ApiResponse<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminService.getAllAdmins();
        return ApiResponse.success(admins);
    }

    @GetMapping("/{id}")
    public ApiResponse<Admin> getAdminById(@PathVariable Long id) {
        Admin admin = adminService.findById(id);
        return ApiResponse.success(admin);
    }

    @PostMapping
    public ApiResponse<Admin> createAdmin(@RequestBody Admin admin) {
        Admin createdAdmin = adminService.createAdmin(admin);
        return ApiResponse.success("管理员创建成功", createdAdmin);
    }

    @PutMapping("/{id}")
    public ApiResponse<Admin> updateAdmin(
            @PathVariable Long id,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String name) {
        Admin updatedAdmin = adminService.updateAdmin(id, phone, email, name);
        return ApiResponse.success("管理员信息更新成功", updatedAdmin);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ApiResponse.success("管理员删除成功");
    }
}
