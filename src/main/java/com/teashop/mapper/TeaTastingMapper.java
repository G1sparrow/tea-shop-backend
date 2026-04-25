package com.teashop.mapper;

import com.teashop.common.entity.TeaTasting;

public interface TeaTastingMapper {
    TeaTasting findById(Long id);
    TeaTasting findByProductId(Long productId);
    int insert(TeaTasting teaTasting);
    int update(TeaTasting teaTasting);
    int deleteById(Long id);
}
