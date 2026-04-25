package com.teashop.controller.admin;

import com.teashop.dto.response.ApiResponse;
import com.teashop.common.entity.TeaTracing;
import com.teashop.common.entity.TeaTasting;
import com.teashop.common.entity.BrewingGuide;
import com.teashop.service.TeaTracingService;
import com.teashop.service.TeaTastingService;
import com.teashop.service.BrewingGuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductDetailController {

    @Autowired
    private TeaTracingService teaTracingService;

    @Autowired
    private TeaTastingService teaTastingService;

    @Autowired
    private BrewingGuideService brewingGuideService;

    // 溯源信息管理
    @PostMapping("/{productId}/tracing")
    public ApiResponse<TeaTracing> createTracing(@PathVariable Long productId, @RequestBody TeaTracing teaTracing) {
        // 设置商品ID
        com.teashop.common.entity.Product product = new com.teashop.common.entity.Product();
        product.setId(productId);
        teaTracing.setProduct(product);
        
        TeaTracing createdTracing = teaTracingService.createTracing(teaTracing);
        return ApiResponse.success("创建溯源信息成功", createdTracing);
    }

    @PutMapping("/tracing/{id}")
    public ApiResponse<TeaTracing> updateTracing(@PathVariable Long id, @RequestBody TeaTracing teaTracing) {
        TeaTracing updatedTracing = teaTracingService.updateTracing(id, teaTracing);
        return ApiResponse.success("更新溯源信息成功", updatedTracing);
    }

    @DeleteMapping("/tracing/{id}")
    public ApiResponse<String> deleteTracing(@PathVariable Long id) {
        teaTracingService.deleteTracing(id);
        return ApiResponse.success("删除溯源信息成功");
    }

    // 品鉴信息管理
    @PostMapping("/{productId}/tasting")
    public ApiResponse<TeaTasting> createTasting(@PathVariable Long productId, @RequestBody TeaTasting teaTasting) {
        // 设置商品ID
        com.teashop.common.entity.Product product = new com.teashop.common.entity.Product();
        product.setId(productId);
        teaTasting.setProduct(product);
        
        TeaTasting createdTasting = teaTastingService.createTasting(teaTasting);
        return ApiResponse.success("创建品鉴信息成功", createdTasting);
    }

    @PutMapping("/tasting/{id}")
    public ApiResponse<TeaTasting> updateTasting(@PathVariable Long id, @RequestBody TeaTasting teaTasting) {
        TeaTasting updatedTasting = teaTastingService.updateTasting(id, teaTasting);
        return ApiResponse.success("更新品鉴信息成功", updatedTasting);
    }

    @DeleteMapping("/tasting/{id}")
    public ApiResponse<String> deleteTasting(@PathVariable Long id) {
        teaTastingService.deleteTasting(id);
        return ApiResponse.success("删除品鉴信息成功");
    }

    // 冲泡指南管理
    @PostMapping("/{productId}/brewing-guide")
    public ApiResponse<BrewingGuide> createBrewingGuide(@PathVariable Long productId, @RequestBody BrewingGuide brewingGuide) {
        // 设置商品ID
        com.teashop.common.entity.Product product = new com.teashop.common.entity.Product();
        product.setId(productId);
        brewingGuide.setProduct(product);
        
        BrewingGuide createdGuide = brewingGuideService.createBrewingGuide(brewingGuide);
        return ApiResponse.success("创建冲泡指南成功", createdGuide);
    }

    @PutMapping("/brewing-guide/{id}")
    public ApiResponse<BrewingGuide> updateBrewingGuide(@PathVariable Long id, @RequestBody BrewingGuide brewingGuide) {
        BrewingGuide updatedGuide = brewingGuideService.updateBrewingGuide(id, brewingGuide);
        return ApiResponse.success("更新冲泡指南成功", updatedGuide);
    }

    @DeleteMapping("/brewing-guide/{id}")
    public ApiResponse<String> deleteBrewingGuide(@PathVariable Long id) {
        brewingGuideService.deleteBrewingGuide(id);
        return ApiResponse.success("删除冲泡指南成功");
    }
}
