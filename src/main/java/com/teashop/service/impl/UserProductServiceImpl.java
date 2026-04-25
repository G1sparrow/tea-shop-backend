package com.teashop.service.impl;

import com.teashop.dto.response.ProductResponse;
import com.teashop.common.entity.Product;
import com.teashop.common.entity.ProductCategory;
import com.teashop.common.exception.ResourceNotFoundException;
import com.teashop.mapper.ProductMapper;
import com.teashop.service.UserProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserProductServiceImpl implements UserProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        int offset = (int) pageable.getOffset();
        int limit = pageable.getPageSize();
        
        // 使用数据库级别的分页而不是内存分页
        List<Product> products = productMapper.findByStatusTrueAndKeyword("", offset, limit);
        int totalCount = productMapper.countByStatusTrue();
        
        List<ProductResponse> responses = products.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        
        return new org.springframework.data.domain.PageImpl<>(responses, pageable, totalCount);
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productMapper.findById(id);
        if (product == null) {
            throw new ResourceNotFoundException("商品不存在");
        }
        return convertToResponse(product);
    }

    @Override
    public List<ProductResponse> getProductsByCategory(ProductCategory category) {
        List<Product> products = productMapper.findByCategory(category);
        return products.stream()
                .filter(p -> p.getStatus()) // 只返回上架的产品
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductResponse> searchProducts(String keyword, Pageable pageable) {
        int offset = (int) pageable.getOffset();
        int limit = pageable.getPageSize();
        List<Product> products = productMapper.findByStatusTrueAndKeyword(keyword, offset, limit);
        List<ProductResponse> responses = products.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        
        int totalCount = keyword != null ? productMapper.countByStatusTrueAndKeyword(keyword) : productMapper.countByStatusTrue();
        return new org.springframework.data.domain.PageImpl<>(responses, pageable, totalCount);
    }

    @Override
    public List<ProductResponse> getAllProductsByCategory(ProductCategory productCategory) {
        List<Product> products = productMapper.findByCategory(productCategory);
        return products.stream()
                .filter(p -> p.getStatus()) // 只返回上架的产品
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private ProductResponse convertToResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setImageUrl(product.getImageUrl());
        response.setCategory(product.getCategory());
        response.setStock(product.getStock());
        response.setStatus(product.getStatus());
        response.setCreateTime(product.getCreateTime());
        response.setUpdateTime(product.getUpdateTime());
        return response;
    }
}