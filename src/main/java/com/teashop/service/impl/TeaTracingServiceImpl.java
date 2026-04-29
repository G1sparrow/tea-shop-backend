package com.teashop.service.impl;

import com.teashop.service.TeaTracingService;
import com.teashop.common.entity.TeaTracing;
import com.teashop.common.exception.ResourceNotFoundException;
import com.teashop.mapper.TeaTracingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TeaTracingServiceImpl implements TeaTracingService {

    @Autowired
    private TeaTracingMapper teaTracingMapper;

    @Override
    public TeaTracing findById(Long id) {
        TeaTracing teaTracing = teaTracingMapper.findById(id);
        if (teaTracing == null) {
            throw new ResourceNotFoundException("溯源信息不存在");
        }
        return teaTracing;
    }

    @Override
    public TeaTracing findByProductId(Long productId) {
        return teaTracingMapper.findByProductId(productId);
    }

    @Override
    public TeaTracing createTracing(TeaTracing teaTracing) {
        int result = teaTracingMapper.insert(teaTracing);
        if (result <= 0) {
            throw new RuntimeException("创建溯源信息失败");
        }
        return teaTracing;
    }

    @Override
    public TeaTracing updateTracing(Long id, TeaTracing teaTracing) {
        TeaTracing existingTracing = teaTracingMapper.findById(id);
        if (existingTracing == null) {
            throw new ResourceNotFoundException("溯源信息不存在");
        }

        teaTracing.setId(id);
        int result = teaTracingMapper.update(teaTracing);
        if (result <= 0) {
            throw new RuntimeException("更新溯源信息失败");
        }
        return teaTracing;
    }

    @Override
    public void deleteTracing(Long id) {
        TeaTracing teaTracing = teaTracingMapper.findById(id);
        if (teaTracing == null) {
            throw new ResourceNotFoundException("溯源信息不存在");
        }

        int result = teaTracingMapper.deleteById(id);
        if (result <= 0) {
            throw new RuntimeException("删除溯源信息失败");
        }
    }

    @Override
    public TeaTracing save(TeaTracing teaTracing) {
        // 检查是否已存在该商品的溯源信息
        Long productId = teaTracing.getProduct().getId();
        TeaTracing existing = findByProductId(productId);
        if (existing != null) {
            // 更新现有记录
            teaTracing.setId(existing.getId());
            int result = teaTracingMapper.update(teaTracing);
            if (result <= 0) {
                throw new RuntimeException("更新溯源信息失败");
            }
            return teaTracing;
        } else {
            // 创建新记录
            int result = teaTracingMapper.insert(teaTracing);
            if (result <= 0) {
                throw new RuntimeException("创建溯源信息失败");
            }
            return teaTracing;
        }
    }
}