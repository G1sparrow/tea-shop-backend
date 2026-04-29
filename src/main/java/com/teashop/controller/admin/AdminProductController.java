package com.teashop.controller.admin;

import com.teashop.dto.response.ApiResponse;
import com.teashop.dto.response.ProductResponse;
import com.teashop.common.entity.ProductCategory;
import com.teashop.service.AdminProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

    @Autowired
    private AdminProductService productService;

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
            // 映射前端传入的分类字符串到枚举值
            switch (categoryStr.toUpperCase()) {
                case "GREEN_TEA":
                    return ProductCategory.GREEN_TEA;
                case "OOLONG_TEA":
                    return ProductCategory.OOLONG_TEA;
                case "BLACK_TEA":
                    return ProductCategory.BLACK_TEA;
                case "WHITE_TEA":
                    return ProductCategory.WHITE_TEA;
                case "PUERH_TEA":
                    return ProductCategory.PUERH_TEA;
                case "TEA_SET":
                    return ProductCategory.TEA_SET;
                // 保持向后兼容
                case "GREEN":
                    return ProductCategory.GREEN_TEA;
                case "OOLONG":
                    return ProductCategory.OOLONG_TEA;
                case "BLACK":
                    return ProductCategory.BLACK_TEA;
                default:
                    throw new IllegalArgumentException("无效的商品分类: " + categoryStr);
            }
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
    
    @PostMapping
    public ApiResponse<ProductResponse> createProduct(@RequestBody com.teashop.common.entity.Product product) {
        // 设置默认值
        if (product.getStock() == null) {
            product.setStock(0);
        }
        if (product.getStatus() == null) {
            product.setStatus(true);
        }
        
        ProductResponse createdProduct = productService.createProduct(product);
        return ApiResponse.success(createdProduct);
    }
    
    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody com.teashop.common.entity.Product product) {
        // 确保库存和状态字段被正确处理
        if (product.getStock() == null) {
            product.setStock(0);
        }
        if (product.getStatus() == null) {
            product.setStatus(true);
        }
        
        ProductResponse updatedProduct = productService.updateProduct(id, product);
        return ApiResponse.success(updatedProduct);
    }
    
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ApiResponse.success("商品删除成功");
    }
    
    @DeleteMapping("/batch")
    public ApiResponse<String> batchDeleteProduct(@RequestBody java.util.Map<String, java.util.List<Long>> request) {
        java.util.List<Long> ids = request.get("ids");
        if (ids == null || ids.isEmpty()) {
            return ApiResponse.error("请选择要删除的商品");
        }
        productService.batchDeleteProduct(ids);
        return ApiResponse.success("批量删除成功");
    }
    
    @PutMapping("/{id}/status")
    public ApiResponse<ProductResponse> updateProductStatus(@PathVariable Long id, @RequestBody java.util.Map<String, Boolean> request) {
        Boolean status = request.get("status");
        if (status == null) {
            return ApiResponse.error("请指定商品状态");
        }
        ProductResponse updatedProduct = productService.updateProductStatus(id, status);
        return ApiResponse.success(updatedProduct);
    }
}