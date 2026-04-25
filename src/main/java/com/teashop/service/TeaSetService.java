package com.teashop.service;

import com.teashop.common.entity.TeaSet;
import com.teashop.dto.request.TeaSetRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeaSetService {
    /**
     * 获取茶器列表（支持分页、排序、搜索）
     */
    Page<TeaSet> getTeaSets(Pageable pageable, String keyword, String category);

    /**
     * 根据ID获取茶器详情
     */
    TeaSet getTeaSetById(Long id);

    /**
     * 新增茶器
     */
    TeaSet createTeaSet(TeaSetRequest request);

    /**
     * 更新茶器
     */
    TeaSet updateTeaSet(Long id, TeaSetRequest request);

    /**
     * 删除茶器
     */
    void deleteTeaSet(Long id);

    /**
     * 批量删除茶器
     */
    void batchDeleteTeaSets(List<Long> ids);

    /**
     * 更新茶器状态
     */
    TeaSet updateTeaSetStatus(Long id, Boolean status);
}