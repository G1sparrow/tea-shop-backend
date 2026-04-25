package com.teashop.service.impl;

import com.teashop.service.BrewingGuideService;
import com.teashop.common.entity.BrewingGuide;
import com.teashop.common.exception.ResourceNotFoundException;
import com.teashop.mapper.BrewingGuideMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BrewingGuideServiceImpl implements BrewingGuideService {

    @Autowired
    private BrewingGuideMapper brewingGuideMapper;

    @Override
    public BrewingGuide findById(Long id) {
        BrewingGuide brewingGuide = brewingGuideMapper.findById(id);
        if (brewingGuide == null) {
            throw new ResourceNotFoundException("冲泡指南不存在");
        }
        return brewingGuide;
    }

    @Override
    public BrewingGuide findByProductId(Long productId) {
        return brewingGuideMapper.findByProductId(productId);
    }

    @Override
    public BrewingGuide createBrewingGuide(BrewingGuide brewingGuide) {
        int result = brewingGuideMapper.insert(brewingGuide);
        if (result <= 0) {
            throw new RuntimeException("创建冲泡指南失败");
        }
        return brewingGuide;
    }

    @Override
    public BrewingGuide updateBrewingGuide(Long id, BrewingGuide brewingGuide) {
        BrewingGuide existingGuide = brewingGuideMapper.findById(id);
        if (existingGuide == null) {
            throw new ResourceNotFoundException("冲泡指南不存在");
        }

        brewingGuide.setId(id);
        int result = brewingGuideMapper.update(brewingGuide);
        if (result <= 0) {
            throw new RuntimeException("更新冲泡指南失败");
        }
        return brewingGuide;
    }

    @Override
    public void deleteBrewingGuide(Long id) {
        BrewingGuide brewingGuide = brewingGuideMapper.findById(id);
        if (brewingGuide == null) {
            throw new ResourceNotFoundException("冲泡指南不存在");
        }

        int result = brewingGuideMapper.deleteById(id);
        if (result <= 0) {
            throw new RuntimeException("删除冲泡指南失败");
        }
    }
}
