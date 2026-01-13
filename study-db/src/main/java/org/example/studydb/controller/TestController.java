package org.example.studydb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.studydb.util.Result;
import org.example.studydb.util.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通用测试Controller
 * 解决静态资源找不到的问题，仅返回成功提示
 */
@RestController
@RequestMapping("/api/test")
@Tag(name = "通用测试模块", description = "基础测试接口，返回成功提示")
public class TestController {

    /**
     * 极简测试接口 - 返回成功（匹配项目Result规范）
     */
    @GetMapping("/success")
    @Operation(summary = "测试成功返回", description = "最基础的接口，仅返回“成功”提示")
    public Result<String> testSuccess() {

        return Result.success("成功");
    }

    /**
     * 备选：兼容UserController的ResultUtil风格
     */
    @GetMapping("/simple-success")
    @Operation(summary = "纯文本成功返回", description = "兼容User模块的ResultUtil格式")
    public ResultUtil<String> testSimpleSuccess() {
        return ResultUtil.success("成功", "操作成功");
    }
}