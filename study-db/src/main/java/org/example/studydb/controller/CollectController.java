package org.example.studydb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.example.studydb.model.dto.CollectDTO;
import org.example.studydb.model.dto.CollectQueryDTO;
import org.example.studydb.model.vo.CollectVO;
import org.example.studydb.model.vo.PageVO;
import org.example.studydb.service.CollectService;
import org.example.studydb.util.Result;
import org.springframework.web.bind.annotation.*;

/**
 * 收藏模块Controller
 * 已优化：从HttpServletRequest获取登录用户ID，实现数据隔离
 */
@RestController
@RequestMapping("/api/collect")
@Tag(name = "收藏模块", description = "收藏的增删改查、分页查询接口")
public class CollectController {

    @Resource
    private CollectService collectService;

    /**
     * 新增收藏
     */
    @PostMapping("/add")
    @Operation(summary = "新增收藏", description = "新增当前登录用户的收藏，关联指定分类，校验链接唯一性")
    public Result<CollectVO> addCollect(HttpServletRequest request,
                                        @Valid @RequestBody CollectDTO collectDTO) {
        Long userId = (Long) request.getAttribute("loginUserId");
        if (userId == null) {
            return Result.fail(401, "请先登录后再操作");
        }
        CollectVO collectVO = collectService.addCollect(userId, collectDTO);
        return Result.success("新增收藏成功", collectVO);
    }

    /**
     * 修改收藏
     */
    @PutMapping("/update")
    @Operation(summary = "修改收藏", description = "修改当前登录用户的已有收藏，校验收藏归属权和链接唯一性")
    public Result<CollectVO> updateCollect(HttpServletRequest request,
                                           @Valid @RequestBody CollectDTO collectDTO) {
        Long userId = (Long) request.getAttribute("loginUserId");
        if (userId == null) {
            return Result.fail(401, "请先登录后再操作");
        }
        CollectVO collectVO = collectService.updateCollect(userId, collectDTO);
        return Result.success("修改收藏成功", collectVO);
    }

    /**
     * 删除收藏
     */
    @DeleteMapping("/delete/{collectId}")
    @Operation(summary = "删除收藏", description = "逻辑删除当前登录用户的收藏，校验收藏归属权")
    public Result<Boolean> deleteCollect(HttpServletRequest request,
                                         @PathVariable Long collectId) {
        Long userId = (Long) request.getAttribute("loginUserId");
        if (userId == null) {
            return Result.fail(401, "请先登录后再操作");
        }
        Boolean success = collectService.deleteCollect(userId, collectId);
        return Result.success("删除收藏成功", success);
    }

    /**
     * 查询收藏详情
     */
    @GetMapping("/detail/{collectId}")
    @Operation(summary = "查询收藏详情", description = "查询当前登录用户的收藏详情，校验收藏归属权")
    public Result<CollectVO> getCollectDetail(HttpServletRequest request,
                                              @PathVariable Long collectId) {
        Long userId = (Long) request.getAttribute("loginUserId");
        if (userId == null) {
            return Result.fail(401, "请先登录后再操作");
        }
        CollectVO collectVO = collectService.getCollectDetail(userId, collectId);
        return Result.success(collectVO);
    }

    /**
     * 分页查询收藏列表
     */
    @PostMapping("/page")
    @Operation(summary = "分页查询收藏列表", description = "多条件分页查询当前登录用户的收藏列表")
    public Result<PageVO<CollectVO>> pageCollect(HttpServletRequest request,
                                                 @RequestBody CollectQueryDTO collectQueryDTO) {
        Long userId = (Long) request.getAttribute("loginUserId");
        if (userId == null) {
            return Result.fail(401, "请先登录后再操作");
        }
        PageVO<CollectVO> pageVO = collectService.pageCollect(userId, collectQueryDTO);
        return Result.success(pageVO);
    }
}