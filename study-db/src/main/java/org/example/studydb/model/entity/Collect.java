package org.example.studydb.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 收藏网页表实体
 * 对应数据库表：collect
 */
@Data
@TableName("collect")
public class Collect {

    /**
     * 主键ID（自增）
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 收藏标题
     */
    @TableField("title")
    private String title;

    /**
     * 网页链接
     */
    @TableField("url")
    private String url;

    /**
     * 描述信息（可选）
     */
    @TableField("description")
    private String description;

    /**
     * 所属分类ID（关联collect_category表id）
     */
    @TableField("category_id")
    private Long categoryId;

    /**
     * 所属用户ID（关联user表id）
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 收藏时间（自动填充，与createTime一致，单独字段便于查询）
     */
    @TableField(value = "collect_time", fill = FieldFill.INSERT)
    private LocalDateTime collectTime;

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