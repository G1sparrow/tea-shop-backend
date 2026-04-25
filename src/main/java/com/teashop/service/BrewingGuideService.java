package com.teashop.service;

import com.teashop.common.entity.BrewingGuide;

public interface BrewingGuideService {
    BrewingGuide findById(Long id);
    BrewingGuide findByProductId(Long productId);
    BrewingGuide createBrewingGuide(BrewingGuide brewingGuide);
    BrewingGuide updateBrewingGuide(Long id, BrewingGuide brewingGuide);
    void deleteBrewingGuide(Long id);
}
