package org.example.studydb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.studydb.model.dto.TodoDTO;
import org.example.studydb.model.dto.TodoQueryDTO;
import org.example.studydb.model.entity.Todo;
import org.example.studydb.model.vo.PageVO;
import org.example.studydb.model.vo.TodoVO;

/**
 * 待办 Service 接口
 */
public interface TodoService extends IService<Todo> {

    /**
     * 新增待办
     * @param userId 用户 ID
     * @param todoDTO 待办请求参数
     * @return 新增的待办 VO
     */
    TodoVO addTodo(Long userId, TodoDTO todoDTO);

    /**
     * 修改待办
     * @param userId 用户 ID
     * @param todoDTO 待办请求参数
     * @return 修改后的待办 VO
     */
    TodoVO updateTodo(Long userId, TodoDTO todoDTO);

    /**
     * 删除待办（逻辑删除）
     * @param userId 用户 ID
     * @param todoId 待办 ID
     * @return 是否删除成功
     */
    Boolean deleteTodo(Long userId, Long todoId);

    /**
     * 修改待办状态（完成/取消完成）
     * @param userId 用户 ID
     * @param todoId 待办 ID
     * @param status 目标状态（0：未开始，1：进行中，2：已完成）
     * @return 修改后的待办 VO
     */
    TodoVO updateTodoStatus(Long userId, Long todoId, Integer status);

    /**
     * 根据 ID 查询待办详情
     * @param userId 用户 ID
     * @param todoId 待办 ID
     * @return 待办详情 VO
     */
    TodoVO getTodoDetail(Long userId, Long todoId);

    /**
     * 分页查询待办列表
     * @param userId 用户 ID
     * @param todoQueryDTO 查询参数
     * @return 分页 VO
     */
    PageVO<TodoVO> pageTodo(Long userId, TodoQueryDTO todoQueryDTO);

    /**
     * 统计用户待办总数
     * @param userId 用户 ID
     * @return 待办总数
     */
    Long countTotalByUserId(Long userId);

    /**
     * 统计用户已完成待办总数
     * @param userId 用户 ID
     * @return 已完成待办总数
     */
    Long countFinishTotalByUserId(Long userId);
}