package org.example.studydb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.studydb.model.entity.Todo;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * 待办表Mapper接口
 */

public interface TodoMapper extends BaseMapper<Todo> {

    /**
     * 按用户ID+多条件分页查询待办列表
     * @param page 分页对象
     * @param userId 用户ID
     * @param priority 优先级（可选，可为null）
     * @param status 状态（可选，可为null）
     * @param deadlineStart 截止时间开始区间（可选，可为null）
     * @param deadlineEnd 截止时间结束区间（可选，可为null）
     * @return 分页待办列表
     */
    IPage<Todo> selectPageByCondition(IPage<Todo> page,
                                      @Param("userId") Long userId,
                                      @Param("priority") Integer priority,
                                      @Param("status") Integer status,
                                      @Param("deadlineStart") LocalDateTime deadlineStart,
                                      @Param("deadlineEnd") LocalDateTime deadlineEnd);

    /**
     * 统计用户待办总数
     * @param userId 用户ID
     * @return 待办总数
     */
    Long countTotalByUserId(@Param("userId") Long userId);

    /**
     * 统计用户已完成待办总数
     * @param userId 用户ID
     * @return 已完成待办总数
     */
    Long countFinishTotalByUserId(@Param("userId") Long userId);
}