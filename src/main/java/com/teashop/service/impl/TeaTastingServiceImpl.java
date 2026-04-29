package com.teashop.service.impl;

import com.teashop.service.TeaTastingService;
import com.teashop.common.entity.TeaTasting;
import com.teashop.common.exception.ResourceNotFoundException;
import com.teashop.mapper.TeaTastingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TeaTastingServiceImpl implements TeaTastingService {

    @Autowired
    private TeaTastingMapper teaTastingMapper;

    @Override
    public TeaTasting findById(Long id) {
        TeaTasting teaTasting = teaTastingMapper.findById(id);
        if (teaTasting == null) {
            throw new ResourceNotFoundException("品鉴信息不存在");
        }
        return teaTasting;
    }

    @Override
    public TeaTasting findByProductId(Long productId) {
        return teaTastingMapper.findByProductId(productId);
    }

    @Override
    public TeaTasting createTasting(TeaTasting teaTasting) {
        int result = teaTastingMapper.insert(teaTasting);
        if (result <= 0) {
            throw new RuntimeException("创建品鉴信息失败");
        }
        return teaTasting;
    }

    @Override
    public TeaTasting updateTasting(Long id, TeaTasting teaTasting) {
        TeaTasting existingTasting = teaTastingMapper.findById(id);
        if (existingTasting == null) {
            throw new ResourceNotFoundException("品鉴信息不存在");
        }

        teaTasting.setId(id);
        int result = teaTastingMapper.update(teaTasting);
        if (result <= 0) {
            throw new RuntimeException("更新品鉴信息失败");
        }
        return teaTasting;
    }

    @Override
    public void deleteTasting(Long id) {
        TeaTasting teaTasting = teaTastingMapper.findById(id);
        if (teaTasting == null) {
            throw new ResourceNotFoundException("品鉴信息不存在");
        }

        int result = teaTastingMapper.deleteById(id);
        if (result <= 0) {
            throw new RuntimeException("删除品鉴信息失败");
        }
    }

    @Override
    public TeaTasting save(TeaTasting teaTasting) {
        // 检查是否已存在该商品的品鉴信息
        Long productId = teaTasting.getProduct().getId();
        TeaTasting existing = findByProductId(productId);
        if (existing != null) {
            // 更新现有记录
            teaTasting.setId(existing.getId());
            int result = teaTastingMapper.update(teaTasting);
            if (result <= 0) {
                throw new RuntimeException("更新品鉴信息失败");
            }
            return teaTasting;
        } else {
            // 创建新记录
            int result = teaTastingMapper.insert(teaTasting);
            if (result <= 0) {
                throw new RuntimeException("创建品鉴信息失败");
            }
            return teaTasting;
        }
    }
}