package org.example.studydb.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 笔记新增/编辑请求DTO
 */
@Data
@Schema(description = "笔记新增/编辑请求参数")
public class NoteDTO {

    /**
     * 笔记ID（编辑时必填，新增时为null）
     */
    @Schema(description = "笔记ID（编辑必填）", example = "1")
    private Long id;

    /**
     * 笔记标题
     */
    @NotBlank(message = "笔记标题不能为空")
    @Size(max = 100, message = "笔记标题长度不能超过100")
    @Schema(description = "笔记标题", example = "Spring Boot 3.x 学习笔记")
    private String title;

    /**
     * 笔记内容
     */
    @NotBlank(message = "笔记内容不能为空")
    @Schema(description = "笔记内容（富文本）", example = "<p>这是一篇Spring Boot学习笔记...</p>")
    private String content;

    /**
     * 分类ID
     */
    @NotNull(message = "笔记分类不能为空")
    @Schema(description = "分类ID", example = "1")
    private Long categoryId;

    /**
     * 标签（多个标签用逗号分隔）
     */
    @Size(max = 200, message = "标签长度不能超过200")
    @Schema(description = "标签（逗号分隔）", example = "Java,SpringBoot,MyBatis-Plus")
    private String tags;
}