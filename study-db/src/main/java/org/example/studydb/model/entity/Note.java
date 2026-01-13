package org.example.studydb.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 笔记表实体
 * 对应数据库表：note
 */
@Data
@TableName("note")
public class Note {

    /**
     * 主键ID（自增）
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 笔记标题
     */
    @TableField("title")
    private String title;

    /**
     * 笔记内容（富文本内容）
     */
    @TableField("content")
    private String content;

    /**
     * 所属分类ID（关联note_category表id）
     */
    @TableField("category_id")
    private Long categoryId;

    /**
     * 标签（多个标签用逗号分隔，如：Java,SpringBoot）
     */
    @TableField("tags")
    private String tags;

    /**
     * 所属用户ID（关联user表id）
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 阅读量（可选）
     */
    @TableField("view_count")
    private Integer viewCount;

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