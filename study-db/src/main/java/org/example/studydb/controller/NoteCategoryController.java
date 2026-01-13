package org.example.studydb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.example.studydb.model.entity.NoteCategory;
import org.example.studydb.model.vo.NoteCategoryVO;
import org.example.studydb.service.NoteCategoryService;
import org.example.studydb.util.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 笔记分类模块Controller
 * 已优化：从HttpServletRequest获取登录用户ID，移除路径占位userId
 */
@RestController
@RequestMapping("/api/note/category")
@Tag(name = "笔记分类模块", description = "笔记分类的增删改查接口")
public class NoteCategoryController {

    @Resource
    private NoteCategoryService noteCategoryService;

    /**
     * 查询当前登录用户的笔记分类列表
     */
    @GetMapping("/list")
    @Operation(summary = "查询笔记分类列表", description = "查询当前登录用户所有笔记分类，带分类下笔记数量")
    public Result<List<NoteCategoryVO>> listCategory(HttpServletRequest request) {
        // 从request中获取JWT拦截器解析后的登录用户ID
        Long userId = (Long) request.getAttribute("loginUserId");
        // 非空校验，避免空指针，返回友好错误信息
        if (userId == null) {
            return Result.fail(401, "请先登录后再操作");
        }
        List<NoteCategoryVO> categoryVOList = noteCategoryService.listCategoryVO(userId);
        return Result.success(categoryVOList);
    }

    /**
     * 新增笔记分类（当前登录用户）
     */
    @PostMapping("/add")
    @Operation(summary = "新增笔记分类", description = "新增当前登录用户的笔记分类，校验分类名称唯一性")
    public Result<NoteCategory> addCategory(HttpServletRequest request,
                                            @RequestParam String categoryName) {
        Long userId = (Long) request.getAttribute("loginUserId");
        if (userId == null) {
            return Result.fail(401, "请先登录后再操作");
        }
        NoteCategory noteCategory = noteCategoryService.addCategory(userId, categoryName);
        return Result.success("新增分类成功", noteCategory);
    }

    /**
     * 修改笔记分类名称（当前登录用户）
     */
    @PutMapping("/update/{categoryId}")
    @Operation(summary = "修改分类名称", description = "修改当前登录用户的笔记分类名称，校验名称唯一性")
    public Result<Boolean> updateCategoryName(HttpServletRequest request,
                                              @PathVariable Long categoryId,
                                              @RequestParam String newName) {
        Long userId = (Long) request.getAttribute("loginUserId");
        if (userId == null) {
            return Result.fail(401, "请先登录后再操作");
        }
        Boolean success = noteCategoryService.updateCategoryName(userId, categoryId, newName);
        return Result.success("修改分类名称成功", success);
    }

    /**
     * 删除笔记分类（当前登录用户）
     */
    @DeleteMapping("/delete/{categoryId}")
    @Operation(summary = "删除笔记分类", description = "删除当前登录用户的笔记分类，需确保分类下无笔记")
    public Result<Boolean> deleteCategory(HttpServletRequest request,
                                          @PathVariable Long categoryId) {
        Long userId = (Long) request.getAttribute("loginUserId");
        if (userId == null) {
            return Result.fail(401, "请先登录后再操作");
        }
        Boolean success = noteCategoryService.deleteCategory(userId, categoryId);
        return Result.success("删除分类成功", success);
    }
}