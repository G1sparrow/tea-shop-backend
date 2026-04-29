package com.teashop.service;

import com.teashop.common.entity.TeaTracing;

public interface TeaTracingService {
    TeaTracing findById(Long id);
    TeaTracing findByProductId(Long productId);
    TeaTracing createTracing(TeaTracing teaTracing);
    TeaTracing updateTracing(Long id, TeaTracing teaTracing);
    void deleteTracing(Long id);
    TeaTracing save(TeaTracing teaTracing);
}
