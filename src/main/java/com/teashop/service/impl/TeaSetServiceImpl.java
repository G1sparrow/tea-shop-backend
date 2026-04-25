package com.teashop.service.impl;

import com.teashop.common.entity.TeaSet;
import com.teashop.dto.request.TeaSetRequest;
import com.teashop.mapper.TeaSetMapper;
import com.teashop.service.TeaSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeaSetServiceImpl implements TeaSetService {

    @Autowired
    private TeaSetMapper teaSetMapper;

    @Override
    public Page<TeaSet> getTeaSets(Pageable pageable, String keyword, String category) {
        int offset = (int) pageable.getOffset();
        int limit = pageable.getPageSize();
        
        // 获取所有符合条件的茶器
        List<TeaSet> allTeaSets = teaSetMapper.findTeaSets(offset, limit, keyword, category);
        
        // 计算总数
        int totalCount = teaSetMapper.countTeaSets(keyword, category);
        
        // 构建Page对象
        return new org.springframework.data.domain.PageImpl<>(allTeaSets, pageable, totalCount);
    }

    @Override
    public TeaSet getTeaSetById(Long id) {
        return teaSetMapper.findById(id);
    }

    @Override
    public TeaSet createTeaSet(TeaSetRequest request) {
        TeaSet teaSet = new TeaSet();
        teaSet.setName(request.getName());
        teaSet.setDescription(request.getDescription());
        teaSet.setPrice(request.getPrice());
        teaSet.setMaterial(request.getMaterial());
        teaSet.setCategory(request.getCategory());
        teaSet.setStatus(request.getStatus());
        teaSetMapper.save(teaSet);
        return teaSet;
    }

    @Override
    public TeaSet updateTeaSet(Long id, TeaSetRequest request) {
        TeaSet teaSet = teaSetMapper.findById(id);
        if (teaSet == null) {
            throw new RuntimeException("茶器不存在");
        }
        teaSet.setName(request.getName());
        teaSet.setDescription(request.getDescription());
        teaSet.setPrice(request.getPrice());
        teaSet.setMaterial(request.getMaterial());
        teaSet.setCategory(request.getCategory());
        teaSet.setStatus(request.getStatus());
        teaSetMapper.update(teaSet);
        return teaSet;
    }

    @Override
    public void deleteTeaSet(Long id) {
        teaSetMapper.deleteById(id);
    }

    @Override
    public void batchDeleteTeaSets(List<Long> ids) {
        teaSetMapper.batchDeleteByIds(ids);
    }

    @Override
    public TeaSet updateTeaSetStatus(Long id, Boolean status) {
        TeaSet teaSet = teaSetMapper.findById(id);
        if (teaSet == null) {
            throw new RuntimeException("茶器不存在");
        }
        teaSet.setStatus(status);
        teaSetMapper.update(teaSet);
        return teaSet;
    }
}