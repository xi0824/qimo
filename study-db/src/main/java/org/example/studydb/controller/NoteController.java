package org.example.studydb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.example.studydb.model.dto.NoteDTO;
import org.example.studydb.model.dto.NoteQueryDTO;
import org.example.studydb.model.vo.NoteVO;
import org.example.studydb.model.vo.PageVO;
import org.example.studydb.service.NoteService;
import org.example.studydb.util.Result;
import org.springframework.web.bind.annotation.*;

/**
 * 笔记模块Controller
 * 已优化：从HttpServletRequest获取登录用户ID，移除路径占位userId
 */
@RestController
@RequestMapping("/api/note")
@Tag(name = "笔记模块", description = "笔记的增删改查、分页查询接口")
public class NoteController {

    @Resource
    private NoteService noteService;

    /**
     * 新增笔记（当前登录用户）
     */
    @PostMapping("/add")
    @Operation(summary = "新增笔记", description = "新增当前登录用户的笔记，关联指定分类")
    public Result<NoteVO> addNote(HttpServletRequest request,
                                  @Valid @RequestBody NoteDTO noteDTO) {
        Long userId = (Long) request.getAttribute("loginUserId");
        if (userId == null) {
            return Result.fail(401, "请先登录后再操作");
        }
        NoteVO noteVO = noteService.addNote(userId, noteDTO);
        return Result.success("新增笔记成功", noteVO);
    }

    /**
     * 修改笔记（当前登录用户）
     */
    @PutMapping("/update")
    @Operation(summary = "修改笔记", description = "修改当前登录用户的已有笔记，校验笔记归属权")
    public Result<NoteVO> updateNote(HttpServletRequest request,
                                     @Valid @RequestBody NoteDTO noteDTO) {
        Long userId = (Long) request.getAttribute("loginUserId");
        if (userId == null) {
            return Result.fail(401, "请先登录后再操作");
        }
        NoteVO noteVO = noteService.updateNote(userId, noteDTO);
        return Result.success("修改笔记成功", noteVO);
    }

    /**
     * 删除笔记（当前登录用户）
     */
    @DeleteMapping("/delete/{noteId}")
    @Operation(summary = "删除笔记", description = "逻辑删除当前登录用户的笔记，校验笔记归属权")
    public Result<Boolean> deleteNote(HttpServletRequest request,
                                      @PathVariable Long noteId) {
        Long userId = (Long) request.getAttribute("loginUserId");
        if (userId == null) {
            return Result.fail(401, "请先登录后再操作");
        }
        Boolean success = noteService.deleteNote(userId, noteId);
        return Result.success("删除笔记成功", success);
    }

    /**
     * 查询笔记详情（当前登录用户）
     */
    @GetMapping("/detail/{noteId}")
    @Operation(summary = "查询笔记详情", description = "查询当前登录用户的笔记详情，校验笔记归属权")
    public Result<NoteVO> getNoteDetail(HttpServletRequest request,
                                        @PathVariable Long noteId) {
        Long userId = (Long) request.getAttribute("loginUserId");
        if (userId == null) {
            return Result.fail(401, "请先登录后再操作");
        }
        NoteVO noteVO = noteService.getNoteDetail(userId, noteId);
        // 增加阅读量
        noteService.increaseViewCount(noteId);
        return Result.success(noteVO);
    }

    /**
     * 分页查询笔记列表（当前登录用户）
     */
    @PostMapping("/page")
    @Operation(summary = "分页查询笔记列表", description = "多条件分页查询当前登录用户的笔记列表")
    public Result<PageVO<NoteVO>> pageNote(HttpServletRequest request,
                                           @RequestBody NoteQueryDTO noteQueryDTO) {
        Long userId = (Long) request.getAttribute("loginUserId");
        if (userId == null) {
            return Result.fail(401, "请先登录后再操作");
        }
        PageVO<NoteVO> pageVO = noteService.pageNote(userId, noteQueryDTO);
        return Result.success(pageVO);
    }
}