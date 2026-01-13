package org.example.studydb.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 番茄钟表实体
 * 对应数据库表：tomato
 */
@Data
@TableName("tomato")
public class Tomato {

    /**
     * 主键ID（自增）
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 番茄钟标题（关联待办或自定义）
     */
    @TableField("title")
    private String title;

    /**
     * 时长（分钟，默认25分钟）
     */
    @TableField("duration")
    private Integer duration;

    /**
     * 所属用户ID（关联user表id）
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 关联待办ID（可选，关联todo表id）
     */
    @TableField("todo_id")
    private Long todoId;

    /**
     * 开始时间
     */
    @TableField("start_time")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @TableField("end_time")
    private LocalDateTime endTime;

    /**
     * 创建时间（自动填充）
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间（自动填充）
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 逻辑删除标记（0：未删除，1：已删除）
     */
    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;
}