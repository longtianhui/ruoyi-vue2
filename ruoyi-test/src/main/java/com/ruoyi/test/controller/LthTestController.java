package com.ruoyi.test.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试控制器
 */
@RestController//有了这个注解最好不写@ResponseBody注解
@RequestMapping("/test")
public class LthTestController extends BaseController {

    /**
     * 无需权限的测试接口
     */
    @GetMapping("/public/getData")
    public AjaxResult getPublicData() {
        return AjaxResult.success("Hello from new test package!");
    }

    /**
     * 分页列表数据
     */
    @PostMapping("/getPage")
    public TableDataInfo getPage() {
        startPage(); //这是注入到第一个sql语句中
        // 创建测试列表
        List<String> testList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            testList.add("测试数据 " + i);
        }
        return getDataTable(testList);
    }

}