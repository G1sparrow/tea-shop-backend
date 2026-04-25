package com.teashop.service;

import com.teashop.common.entity.TeaTasting;

public interface TeaTastingService {
    TeaTasting findById(Long id);
    TeaTasting findByProductId(Long productId);
    TeaTasting createTasting(TeaTasting teaTasting);
    TeaTasting updateTasting(Long id, TeaTasting teaTasting);
    void deleteTasting(Long id);
}
