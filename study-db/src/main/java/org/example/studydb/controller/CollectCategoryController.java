package org.example.studydb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.example.studydb.model.entity.CollectCategory;
import org.example.studydb.model.vo.CollectCategoryVO;
import org.example.studydb.service.CollectCategoryService;
import org.example.studydb.util.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 收藏分类模块Controller
 * 已优化：从HttpServletRequest获取登录用户ID，实现数据隔离
 */
@RestController
@RequestMapping("/api/collect/category")
@Tag(name = "收藏分类模块", description = "收藏分类的增删改查接口")
public class CollectCategoryController {

    @Resource
    private CollectCategoryService collectCategoryService;

    /**
     * 查询当前登录用户的收藏分类列表
     */
    @GetMapping("/list")
    @Operation(summary = "查询收藏分类列表", description = "查询当前登录用户所有收藏分类，带分类下收藏数量")
    public Result<List<CollectCategoryVO>> listCategory(HttpServletRequest request) {
        // 从request中获取JWT拦截器解析后的登录用户ID
        Long userId = (Long) request.getAttribute("loginUserId");
        // 非空校验，避免空指针，返回友好错误信息
        if (userId == null) {
            return Result.fail(401, "请先登录后再操作");
        }
        List<CollectCategoryVO> categoryVOList = collectCategoryService.listCategoryVO(userId);
        return Result.success(categoryVOList);
    }

    /**
     * 新增收藏分类
     */
    @PostMapping("/add")
    @Operation(summary = "新增收藏分类", description = "新增当前登录用户的收藏分类，校验分类名称唯一性")
    public Result<CollectCategory> addCategory(HttpServletRequest request,
                                               @RequestParam String categoryName) {
        Long userId = (Long) request.getAttribute("loginUserId");
        if (userId == null) {
            return Result.fail(401, "请先登录后再操作");
        }
        CollectCategory collectCategory = collectCategoryService.addCategory(userId, categoryName);
        return Result.success("新增分类成功", collectCategory);
    }

    /**
     * 修改收藏分类名称
     */
    @PutMapping("/update/{categoryId}")
    @Operation(summary = "修改分类名称", description = "修改当前登录用户的收藏分类名称，校验名称唯一性")
    public Result<Boolean> updateCategoryName(HttpServletRequest request,
                                              @PathVariable Long categoryId,
                                              @RequestParam String newName) {
        Long userId = (Long) request.getAttribute("loginUserId");
        if (userId == null) {
            return Result.fail(401, "请先登录后再操作");
        }
        Boolean success = collectCategoryService.updateCategoryName(userId, categoryId, newName);
        return Result.success("修改分类名称成功", success);
    }

    /**
     * 删除收藏分类
     */
    @DeleteMapping("/delete/{categoryId}")
    @Operation(summary = "删除收藏分类", description = "删除当前登录用户的收藏分类，需确保分类下无收藏")
    public Result<Boolean> deleteCategory(HttpServletRequest request,
                                          @PathVariable Long categoryId) {
        Long userId = (Long) request.getAttribute("loginUserId");
        if (userId == null) {
            return Result.fail(401, "请先登录后再操作");
        }
        Boolean success = collectCategoryService.deleteCategory(userId, categoryId);
        return Result.success("删除分类成功", success);
    }
}