package com.teashop.service;

import com.teashop.dto.response.ProductResponse;
import com.teashop.common.entity.Product;
import com.teashop.common.entity.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminProductService {
    Page<ProductResponse> getAllProducts(Pageable pageable);
    
    ProductResponse getProductById(Long id);
    
    Page<ProductResponse> getProductsByCategory(ProductCategory category, Pageable pageable);
    
    List<ProductResponse> getAllProductsByCategory(ProductCategory category);
    
    Page<ProductResponse> searchProducts(String keyword, Pageable pageable);
    
    Long getTotalProductCount();
    
    ProductResponse createProduct(Product product);
    
    ProductResponse updateProduct(Long id, Product product);
    
    void deleteProduct(Long id);
    
    void batchDeleteProduct(List<Long> ids);
    
    ProductResponse updateProductStatus(Long id, boolean status);
}