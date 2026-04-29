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
    public ApiResponse<TeaTracing> saveTracing(@PathVariable Long productId, @RequestBody TeaTracing teaTracing) {
        // 设置商品ID
        com.teashop.common.entity.Product product = new com.teashop.common.entity.Product();
        product.setId(productId);
        teaTracing.setProduct(product);
        
        // 使用save方法，如果存在则更新，否则创建
        TeaTracing savedTracing = teaTracingService.save(teaTracing);
        return ApiResponse.success("保存溯源信息成功", savedTracing);
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

    // 获取商品溯源信息
    @GetMapping("/{productId}/tracing")
    public ApiResponse<TeaTracing> getProductTracing(@PathVariable Long productId) {
        TeaTracing tracing = teaTracingService.findByProductId(productId);
        return ApiResponse.success(tracing);
    }

    // 品鉴信息管理
    @PostMapping("/{productId}/tasting")
    public ApiResponse<TeaTasting> saveTasting(@PathVariable Long productId, @RequestBody TeaTasting teaTasting) {
        // 设置商品ID
        com.teashop.common.entity.Product product = new com.teashop.common.entity.Product();
        product.setId(productId);
        teaTasting.setProduct(product);
        
        // 使用save方法，如果存在则更新，否则创建
        TeaTasting savedTasting = teaTastingService.save(teaTasting);
        return ApiResponse.success("保存品鉴信息成功", savedTasting);
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

    // 获取商品品鉴信息
    @GetMapping("/{productId}/tasting")
    public ApiResponse<TeaTasting> getProductTasting(@PathVariable Long productId) {
        TeaTasting tasting = teaTastingService.findByProductId(productId);
        return ApiResponse.success(tasting);
    }

    // 冲泡指南管理
    @PostMapping("/{productId}/brewing-guide")
    public ApiResponse<BrewingGuide> saveBrewingGuide(@PathVariable Long productId, @RequestBody BrewingGuide brewingGuide) {
        // 设置商品ID
        com.teashop.common.entity.Product product = new com.teashop.common.entity.Product();
        product.setId(productId);
        brewingGuide.setProduct(product);
        
        // 使用save方法，如果存在则更新，否则创建
        BrewingGuide savedGuide = brewingGuideService.save(brewingGuide);
        return ApiResponse.success("保存冲泡指南成功", savedGuide);
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

    // 获取商品冲泡指南
    @GetMapping("/{productId}/brewing-guide")
    public ApiResponse<BrewingGuide> getProductBrewingGuide(@PathVariable Long productId) {
        BrewingGuide guide = brewingGuideService.findByProductId(productId);
        return ApiResponse.success(guide);
    }
}