package org.example.studydb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.example.studydb.model.dto.TodoDTO;
import org.example.studydb.model.dto.TodoQueryDTO;
import org.example.studydb.model.vo.PageVO;
import org.example.studydb.model.vo.TodoVO;
import org.example.studydb.service.TodoService;
import org.example.studydb.util.Result;
import org.springframework.web.bind.annotation.*;

/**
 * 待办模块Controller
 * 已优化：从HttpServletRequest获取登录用户ID，实现数据隔离
 */
@RestController
@RequestMapping("/api/todo")
@Tag(name = "待办模块", description = "待办的增删改查、状态修改、分页查询接口")
public class TodoController {

    @Resource
    private TodoService todoService;

    /**
     * 新增待办
     */
    @PostMapping("/add")
    @Operation(summary = "新增待办", description = "新增当前登录用户的待办，校验优先级和状态合法性")
    public Result<TodoVO> addTodo(HttpServletRequest request,
                                  @Valid @RequestBody TodoDTO todoDTO) {
        Long userId = (Long) request.getAttribute("loginUserId");
        if (userId == null) {
            return Result.fail(401, "请先登录后再操作");
        }
        TodoVO todoVO = todoService.addTodo(userId, todoDTO);
        return Result.success("新增待办成功", todoVO);
    }

    /**
     * 修改待办
     */
    @PutMapping("/update")
    @Operation(summary = "修改待办", description = "修改当前登录用户的已有待办，校验待办归属权和参数合法性")
    public Result<TodoVO> updateTodo(HttpServletRequest request,
                                     @Valid @RequestBody TodoDTO todoDTO) {
        Long userId = (Long) request.getAttribute("loginUserId");
        if (userId == null) {
            return Result.fail(401, "请先登录后再操作");
        }
        TodoVO todoVO = todoService.updateTodo(userId, todoDTO);
        return Result.success("修改待办成功", todoVO);
    }

    /**
     * 删除待办
     */
    @DeleteMapping("/delete/{todoId}")
    @Operation(summary = "删除待办", description = "逻辑删除当前登录用户的待办，校验待办归属权")
    public Result<Boolean> deleteTodo(HttpServletRequest request,
                                      @PathVariable Long todoId) {
        Long userId = (Long) request.getAttribute("loginUserId");
        if (userId == null) {
            return Result.fail(401, "请先登录后再操作");
        }
        Boolean success = todoService.deleteTodo(userId, todoId);
        return Result.success("删除待办成功", success);
    }

    /**
     * 修改待办状态
     */
    @PutMapping("/status/{todoId}/{status}")
    @Operation(summary = "修改待办状态", description = "修改当前登录用户的待办状态，自动更新完成时间")
    public Result<TodoVO> updateTodoStatus(HttpServletRequest request,
                                           @PathVariable Long todoId,
                                           @PathVariable Integer status) {
        Long userId = (Long) request.getAttribute("loginUserId");
        if (userId == null) {
            return Result.fail(401, "请先登录后再操作");
        }
        TodoVO todoVO = todoService.updateTodoStatus(userId, todoId, status);
        return Result.success("修改待办状态成功", todoVO);
    }

    /**
     * 查询待办详情
     */
    @GetMapping("/detail/{todoId}")
    @Operation(summary = "查询待办详情", description = "查询当前登录用户的待办详情，校验待办归属权")
    public Result<TodoVO> getTodoDetail(HttpServletRequest request,
                                        @PathVariable Long todoId) {
        Long userId = (Long) request.getAttribute("loginUserId");
        if (userId == null) {
            return Result.fail(401, "请先登录后再操作");
        }
        TodoVO todoVO = todoService.getTodoDetail(userId, todoId);
        return Result.success(todoVO);
    }

    /**
     * 分页查询待办列表
     */
    @PostMapping("/page")
    @Operation(summary = "分页查询待办列表", description = "多条件分页查询当前登录用户的待办列表")
    public Result<PageVO<TodoVO>> pageTodo(HttpServletRequest request,
                                           @RequestBody TodoQueryDTO todoQueryDTO) {
        Long userId = (Long) request.getAttribute("loginUserId");
        if (userId == null) {
            return Result.fail(401, "请先登录后再操作");
        }
        PageVO<TodoVO> pageVO = todoService.pageTodo(userId, todoQueryDTO);
        return Result.success(pageVO);
    }
}