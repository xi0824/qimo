package org.example.studydb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.example.studydb.model.vo.StatCountVO;
import org.example.studydb.service.StatService;
import org.example.studydb.util.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 统计模块Controller
 * 已优化：从HttpServletRequest获取登录用户ID，实现数据隔离
 */
@RestController
@RequestMapping("/api/stat")
@Tag(name = "统计模块", description = "用户数据汇总统计接口")
public class StatController {

    @Resource
    private StatService statService;

    /**
     * 获取当前登录用户的统计数据
     */
    @GetMapping("/count")
    @Operation(summary = "获取统计数据", description = "获取当前登录用户的笔记、收藏、待办汇总数据及待办完成率")
    public Result<StatCountVO> getStatCount(HttpServletRequest request) {
        // 从request中获取JWT拦截器解析后的登录用户ID
        Long userId = (Long) request.getAttribute("loginUserId");
        // 非空校验，避免空指针，返回友好错误信息
        if (userId == null) {
            return Result.fail(401, "请先登录后再操作");
        }
        StatCountVO statCountVO = statService.getStatCount(userId);
        return Result.success(statCountVO);
    }
}