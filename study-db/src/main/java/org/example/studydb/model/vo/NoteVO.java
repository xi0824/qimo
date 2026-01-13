package org.example.studydb.model.vo;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * 笔记响应VO
 */
@Data
@Schema(description = "笔记响应数据")
public class NoteVO {

    /**
     * 笔记ID
     */
    @Schema(description = "笔记ID", example = "1")
    private Long id;

    /**
     * 笔记标题
     */
    @Schema(description = "笔记标题", example = "Spring Boot 3.x 学习笔记")
    private String title;

    /**
     * 笔记内容（富文本）
     */
    @Schema(description = "笔记内容", example = "<p>这是一篇Spring Boot学习笔记...</p>")
    private String content;

    /**
     * 分类ID
     */
    @Schema(description = "分类ID", example = "1")
    private Long categoryId;

    /**
     * 分类名称
     */
    @Schema(description = "分类名称", example = "Java学习")
    private String categoryName;

    /**
     * 标签（多个标签用逗号分隔）
     */
    @Schema(description = "标签", example = "Java,SpringBoot,MyBatis-Plus")
    private String tags;

    /**
     * 阅读量
     */
    @Schema(description = "阅读量", example = "100")
    private Integer viewCount;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", example = "2026-01-01 10:00:00")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", example = "2026-01-02 15:00:00")
    private LocalDateTime updateTime;
}