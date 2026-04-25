package com.teashop.mapper;

import com.teashop.common.entity.TeaSet;

import java.util.List;

public interface TeaSetMapper {
    /**
     * 分页查询茶器列表
     */
    List<TeaSet> findTeaSets(int offset, int limit, String keyword, String category);

    /**
     * 统计茶器数量
     */
    int countTeaSets(String keyword, String category);

    /**
     * 根据ID查询茶器
     */
    TeaSet findById(Long id);

    /**
     * 保存茶器
     */
    void save(TeaSet teaSet);

    /**
     * 更新茶器
     */
    void update(TeaSet teaSet);

    /**
     * 根据ID删除茶器
     */
    void deleteById(Long id);

    /**
     * 批量删除茶器
     */
    void batchDeleteByIds(List<Long> ids);
}