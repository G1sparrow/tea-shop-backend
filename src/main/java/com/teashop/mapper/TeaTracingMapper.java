package com.teashop.mapper;

import com.teashop.common.entity.TeaTracing;

public interface TeaTracingMapper {
    TeaTracing findById(Long id);
    TeaTracing findByProductId(Long productId);
    int insert(TeaTracing teaTracing);
    int update(TeaTracing teaTracing);
    int deleteById(Long id);
}
