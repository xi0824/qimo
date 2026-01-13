package org.example.studydb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.example.studydb.exception.BusinessException;
import org.example.studydb.exception.ErrorCode;
import org.example.studydb.mapper.TodoMapper;
import org.example.studydb.model.dto.TodoDTO;
import org.example.studydb.model.dto.TodoQueryDTO;
import org.example.studydb.model.entity.Todo;
import org.example.studydb.model.vo.PageVO;
import org.example.studydb.model.vo.TodoVO;
import org.example.studydb.service.TodoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

/**
 * 待办 Service 实现类
 */
@Service
public class TodoServiceImpl extends ServiceImpl<TodoMapper, Todo> implements TodoService {

    @Resource
    private TodoMapper todoMapper;

    /**
     * 优先级名称映射
     */
    private static final String[] PRIORITY_NAMES = {"低", "中", "高"};

    /**
     * 状态名称映射
     */
    private static final String[] STATUS_NAMES = {"未开始", "进行中", "已完成"};

    @Override
    public TodoVO addTodo(Long userId, TodoDTO todoDTO) {
        // 1. 校验优先级和状态的合法性
        validatePriorityAndStatus(todoDTO.getPriority(), todoDTO.getStatus());

        // 2. 构建待办实体
        Todo todo = new Todo();
        BeanUtils.copyProperties(todoDTO, todo);
        todo.setUserId(userId);
        // 新待办默认完成时间为 null
        todo.setFinishTime(null);

        // 3. 保存待办
        boolean saveSuccess = this.save(todo);
        if (!saveSuccess) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "新增待办失败");
        }

        // 4. 转换为 VO 并返回
        return convertToVO(todo);
    }

    @Override
    public TodoVO updateTodo(Long userId, TodoDTO todoDTO) {
        Long todoId = todoDTO.getId();
        // 1. 校验待办是否存在且属于当前用户
        Todo todo = validateTodoOwner(userId, todoId);

        // 2. 校验优先级和状态的合法性
        validatePriorityAndStatus(todoDTO.getPriority(), todoDTO.getStatus());

        // 3. 构建更新实体
        BeanUtils.copyProperties(todoDTO, todo);
        // 只有状态为已完成时才更新完成时间
        if (todo.getStatus() == 2) {
            todo.setFinishTime(LocalDateTime.now());
        } else {
            todo.setFinishTime(null);
        }

        // 4. 更新待办
        boolean updateSuccess = this.updateById(todo);
        if (!updateSuccess) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "修改待办失败");
        }

        // 5. 转换为 VO 并返回
        return convertToVO(todo);
    }

    @Override
    public Boolean deleteTodo(Long userId, Long todoId) {
        // 校验待办是否存在且属于当前用户
        Todo todo = validateTodoOwner(userId, todoId);
        // 逻辑删除待办
        return this.removeById(todoId);
    }

    @Override
    public TodoVO updateTodoStatus(Long userId, Long todoId, Integer status) {
        // 1. 校验状态合法性
        if (status < 0 || status > 2) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "状态只能是 0（未开始）、1（进行中）、2（已完成）");
        }

        // 2. 校验待办是否存在且属于当前用户
        Todo todo = validateTodoOwner(userId, todoId);

        // 3. 更新状态和完成时间
        todo.setStatus(status);
        if (status == 2) {
            todo.setFinishTime(LocalDateTime.now());
        } else {
            todo.setFinishTime(null);
        }

        // 4. 保存更新
        boolean updateSuccess = this.updateById(todo);
        if (!updateSuccess) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "修改待办状态失败");
        }

        // 5. 转换为 VO 并返回
        return convertToVO(todo);
    }

    @Override
    public TodoVO getTodoDetail(Long userId, Long todoId) {
        // 校验待办是否存在且属于当前用户
        Todo todo = validateTodoOwner(userId, todoId);
        // 转换为 VO 并返回
        return convertToVO(todo);
    }

    @Override
    public PageVO<TodoVO> pageTodo(Long userId, TodoQueryDTO todoQueryDTO) {
        // 1. 构建分页对象
        Page<Todo> page = new Page<>(todoQueryDTO.getPageNum(), todoQueryDTO.getPageSize());

        // 2. 调用 Mapper 分页查询（使用 IPage 接收，规避类型问题）
        IPage<Todo> todoIPage = todoMapper.selectPageByCondition(
                page,
                userId,
                todoQueryDTO.getPriority(),
                todoQueryDTO.getStatus(),
                todoQueryDTO.getDeadlineStart(),
                todoQueryDTO.getDeadlineEnd()
        );

        // 3. 转换为 VO 分页结果
        Page<TodoVO> voPage = new Page<>(todoIPage.getCurrent(), todoIPage.getSize(), todoIPage.getTotal());
        voPage.setRecords(todoIPage.getRecords().stream().map(this::convertToVO).toList());

        // 4. 封装为通用分页 VO
        return PageVO.build(voPage);
    }

    @Override
    public Long countTotalByUserId(Long userId) {
        return todoMapper.countTotalByUserId(userId);
    }

    @Override
    public Long countFinishTotalByUserId(Long userId) {
        return todoMapper.countFinishTotalByUserId(userId);
    }

    /**
     * 校验待办的所属用户
     */
    private Todo validateTodoOwner(Long userId, Long todoId) {
        Todo todo = this.getById(todoId);
        if (todo == null || !todo.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "待办不存在或无操作权限");
        }
        return todo;
    }

    /**
     * 校验优先级和状态的合法性
     */
    private void validatePriorityAndStatus(Integer priority, Integer status) {
        if (priority == null || priority < 1 || priority > 3) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "优先级只能是 1（低）、2（中）、3（高）");
        }
        if (status == null || status < 0 || status > 2) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "状态只能是 0（未开始）、1（进行中）、2（已完成）");
        }
    }

    /**
     * 将 Todo 实体转换为 TodoVO
     */
    private TodoVO convertToVO(Todo todo) {
        TodoVO vo = new TodoVO();
        BeanUtils.copyProperties(todo, vo);
        // 填充优先级名称
        vo.setPriorityName(PRIORITY_NAMES[todo.getPriority() - 1]);
        // 填充状态名称
        vo.setStatusName(STATUS_NAMES[todo.getStatus()]);
        return vo;
    }
}