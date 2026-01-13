package org.example.studydb.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 收藏分类表实体
 * 对应数据库表：collect_category
 */
@Data
@TableName("collect_category")
public class CollectCategory {

    /**
     * 主键ID（自增）
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 分类名称
     */
    @TableField("category_name")
    private String categoryName;

    /**
     * 所属用户ID（关联user表id）
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 分类排序（用于自定义展示顺序）
     */
    @TableField("sort")
    private Integer sort;

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