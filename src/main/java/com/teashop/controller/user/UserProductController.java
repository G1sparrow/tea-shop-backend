package com.teashop.controller.user;

import com.teashop.dto.response.ApiResponse;
import com.teashop.dto.response.ProductResponse;
import com.teashop.common.entity.ProductCategory;
import com.teashop.service.UserProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/products")
public class UserProductController {

    @Autowired
    private UserProductService productService;

    @GetMapping
    public ApiResponse<Page<ProductResponse>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<ProductResponse> products = productService.getAllProducts(pageable);
        return ApiResponse.success(products);
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getProductById(@PathVariable Long id) {
        ProductResponse product = productService.getProductById(id);
        return ApiResponse.success(product);
    }

    @GetMapping("/category/{category}")
    public ApiResponse<java.util.List<ProductResponse>> getProductsByCategory(@PathVariable String category) {
        ProductCategory productCategory = mapStringToProductCategory(category);
        java.util.List<ProductResponse> products = productService.getAllProductsByCategory(productCategory);
        return ApiResponse.success(products);
    }
    
    private ProductCategory mapStringToProductCategory(String categoryStr) {
        try {
            return ProductCategory.valueOf(categoryStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("无效的商品分类: " + categoryStr);
        }
    }

    @GetMapping("/search")
    public ApiResponse<Page<ProductResponse>> searchProducts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<ProductResponse> products = productService.searchProducts(keyword, pageable);
        return ApiResponse.success(products);
    }


}