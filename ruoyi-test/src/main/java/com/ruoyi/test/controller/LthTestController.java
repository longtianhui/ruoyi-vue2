package com.ruoyi.test.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 */
@RestController
@RequestMapping("/test")
public class LthTestController extends BaseController {

    /**
     * 无需权限的测试接口
     */
    @GetMapping("/public/getData")
    public AjaxResult getPublicData() {
        return AjaxResult.success("Hello from new test package!");
    }
}