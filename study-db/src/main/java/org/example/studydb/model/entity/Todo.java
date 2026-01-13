package org.example.studydb.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 待办事项表实体
 * 对应数据库表：todo
 */
@Data
@TableName("todo")
public class Todo {

    /**
     * 主键ID（自增）
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 待办标题
     */
    @TableField("title")
    private String title;

    /**
     * 待办描述（可选）
     */
    @TableField("description")
    private String description;

    /**
     * 优先级（1：低，2：中，3：高）
     */
    @TableField("priority")
    private Integer priority;

    /**
     * 状态（0：未开始，1：进行中，2：已完成）
     */
    @TableField("status")
    private Integer status;

    /**
     * 截止时间（可选）
     */
    @TableField("deadline")
    private LocalDateTime deadline;

    /**
     * 所属用户ID（关联user表id）
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 完成时间（可选，状态为已完成时填充）
     */
    @TableField("finish_time")
    private LocalDateTime finishTime;

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