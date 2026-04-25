package com.teashop.mapper;

import com.teashop.common.entity.BrewingGuide;

public interface BrewingGuideMapper {
    BrewingGuide findById(Long id);
    BrewingGuide findByProductId(Long productId);
    int insert(BrewingGuide brewingGuide);
    int update(BrewingGuide brewingGuide);
    int deleteById(Long id);
}
