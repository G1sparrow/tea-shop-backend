package com.teashop.service.impl;

import com.teashop.dto.response.ProductResponse;
import com.teashop.common.entity.Product;
import com.teashop.common.entity.ProductCategory;
import com.teashop.common.exception.ResourceNotFoundException;
import com.teashop.mapper.ProductMapper;
import com.teashop.service.AdminProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminProductServiceImpl implements AdminProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        int offset = (int) pageable.getOffset();
        int limit = pageable.getPageSize();
        List<Product> allProducts = productMapper.findByStatusTrue();
        List<Product> paginatedProducts = allProducts.stream()
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());
        
        List<ProductResponse> responses = paginatedProducts.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        
        return new org.springframework.data.domain.PageImpl<>(responses, pageable, allProducts.size());
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
    public Page<ProductResponse> getProductsByCategory(ProductCategory category, Pageable pageable) {
        int offset = (int) pageable.getOffset();
        int limit = pageable.getPageSize();
        List<Product> allProducts = productMapper.findByCategory(category);
        List<Product> paginatedProducts = allProducts.stream()
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());
        
        List<ProductResponse> responses = paginatedProducts.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        
        return new org.springframework.data.domain.PageImpl<>(responses, pageable, allProducts.size());
    }

    @Override
    public List<ProductResponse> getAllProductsByCategory(ProductCategory category) {
        List<Product> products = productMapper.findAll();
        return products.stream()
                .filter(p -> p.getStatus() && (category == null || p.getCategory() == category))
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
    
    @Override
    public Long getTotalProductCount() {
        List<Product> products = productMapper.findAll();
        return (long) products.size();
    }
    
    @Override
    public ProductResponse createProduct(Product product) {
        int result = productMapper.insert(product);
        if (result <= 0) {
            throw new com.teashop.common.exception.BusinessException("创建商品失败");
        }
        return convertToResponse(product);
    }
    
    @Override
    public ProductResponse updateProduct(Long id, Product product) {
        Product existingProduct = productMapper.findById(id);
        if (existingProduct == null) {
            throw new ResourceNotFoundException("商品不存在");
        }
        
        // 更新商品信息
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setStatus(product.getStatus());
        
        int result = productMapper.update(existingProduct);
        if (result <= 0) {
            throw new com.teashop.common.exception.BusinessException("更新商品失败");
        }
        
        return convertToResponse(existingProduct);
    }
    
    @Override
    public void deleteProduct(Long id) {
        Product product = productMapper.findById(id);
        if (product == null) {
            throw new ResourceNotFoundException("商品不存在");
        }
        
        int result = productMapper.delete(id);
        if (result <= 0) {
            throw new com.teashop.common.exception.BusinessException("删除商品失败");
        }
    }
    
    @Override
    public void batchDeleteProduct(List<Long> ids) {
        for (Long id : ids) {
            deleteProduct(id);
        }
    }
    
    @Override
    public ProductResponse updateProductStatus(Long id, boolean status) {
        Product product = productMapper.findById(id);
        if (product == null) {
            throw new ResourceNotFoundException("商品不存在");
        }
        
        product.setStatus(status);
        int result = productMapper.update(product);
        if (result <= 0) {
            throw new com.teashop.common.exception.BusinessException("更新商品状态失败");
        }
        
        return convertToResponse(product);
    }
}