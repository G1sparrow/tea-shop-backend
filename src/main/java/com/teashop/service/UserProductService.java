package com.teashop.service;

import com.teashop.dto.response.ProductResponse;
import com.teashop.common.entity.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserProductService {
    Page<ProductResponse> getAllProducts(Pageable pageable);
    
    ProductResponse getProductById(Long id);
    
    List<ProductResponse> getProductsByCategory(ProductCategory category);
    
    Page<ProductResponse> searchProducts(String keyword, Pageable pageable);

    List<ProductResponse> getAllProductsByCategory(ProductCategory productCategory);
}