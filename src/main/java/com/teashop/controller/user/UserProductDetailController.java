package com.teashop.controller.user;

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
@RequestMapping("/api/user/products")
public class UserProductDetailController {

    @Autowired
    private TeaTracingService teaTracingService;

    @Autowired
    private TeaTastingService teaTastingService;

    @Autowired
    private BrewingGuideService brewingGuideService;

    // 获取商品溯源信息
    @GetMapping("/{productId}/tracing")
    public ApiResponse<TeaTracing> getProductTracing(@PathVariable Long productId) {
        TeaTracing tracing = teaTracingService.findByProductId(productId);
        return ApiResponse.success(tracing);
    }

    // 获取商品品鉴信息
    @GetMapping("/{productId}/tasting")
    public ApiResponse<TeaTasting> getProductTasting(@PathVariable Long productId) {
        TeaTasting tasting = teaTastingService.findByProductId(productId);
        return ApiResponse.success(tasting);
    }

    // 获取商品冲泡指南
    @GetMapping("/{productId}/brewing-guide")
    public ApiResponse<BrewingGuide> getProductBrewingGuide(@PathVariable Long productId) {
        BrewingGuide guide = brewingGuideService.findByProductId(productId);
        return ApiResponse.success(guide);
    }
}
