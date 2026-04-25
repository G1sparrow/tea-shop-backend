package com.teashop.controller.admin;

import com.teashop.dto.request.TeaSetRequest;
import com.teashop.dto.response.ApiResponse;
import com.teashop.common.entity.TeaSet;
import com.teashop.service.TeaSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/tea-sets")
public class AdminTeaSetController {

    @Autowired
    private TeaSetService teaSetService;

    /**
     * 获取茶器列表（支持分页、排序、搜索）
     */
    @GetMapping
    public ApiResponse<Page<TeaSet>> getTeaSets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category) {
        
        // 构建排序对象
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        // 构建分页对象
        Pageable pageable = PageRequest.of(page, size, sort);
        
        // 调用服务获取茶器列表
        Page<TeaSet> teaSets = teaSetService.getTeaSets(pageable, keyword, category);
        return ApiResponse.success(teaSets);
    }

    /**
     * 根据ID获取茶器详情
     */
    @GetMapping("/{id}")
    public ApiResponse<TeaSet> getTeaSetById(@PathVariable Long id) {
        TeaSet teaSet = teaSetService.getTeaSetById(id);
        return ApiResponse.success(teaSet);
    }

    /**
     * 新增茶器
     */
    @PostMapping
    public ApiResponse<TeaSet> createTeaSet(@RequestBody TeaSetRequest request) {
        TeaSet teaSet = teaSetService.createTeaSet(request);
        return ApiResponse.success(teaSet);
    }

    /**
     * 更新茶器
     */
    @PutMapping("/{id}")
    public ApiResponse<TeaSet> updateTeaSet(@PathVariable Long id, @RequestBody TeaSetRequest request) {
        TeaSet teaSet = teaSetService.updateTeaSet(id, request);
        return ApiResponse.success(teaSet);
    }

    /**
     * 删除茶器
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteTeaSet(@PathVariable Long id) {
        teaSetService.deleteTeaSet(id);
        return ApiResponse.success(null);
    }

    /**
     * 批量删除茶器
     */
    @DeleteMapping("/batch")
    public ApiResponse<Void> batchDeleteTeaSets(@RequestBody List<Long> ids) {
        teaSetService.batchDeleteTeaSets(ids);
        return ApiResponse.success(null);
    }

    /**
     * 更新茶器状态
     */
    @PutMapping("/{id}/status")
    public ApiResponse<TeaSet> updateTeaSetStatus(@PathVariable Long id, @RequestParam Boolean status) {
        TeaSet teaSet = teaSetService.updateTeaSetStatus(id, status);
        return ApiResponse.success(teaSet);
    }
}
