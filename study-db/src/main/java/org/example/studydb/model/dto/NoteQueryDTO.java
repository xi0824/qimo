package org.example.studydb.model.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 笔记查询筛选请求DTO
 */
@Data
@Schema(description = "笔记查询筛选请求参数")
public class NoteQueryDTO {

    /**
     * 分类ID（可选，用于筛选指定分类的笔记）
     */
    @Schema(description = "分类ID", example = "1")
    private Long categoryId;

    /**
     * 标签关键词（可选，用于模糊查询标签）
     */
    @Schema(description = "标签关键词", example = "SpringBoot")
    private String tagKeyword;

    /**
     * 标题关键词（可选，用于模糊查询标题）
     */
    @Schema(description = "标题关键词", example = "学习笔记")
    private String titleKeyword;

    /**
     * 页码（默认1）
     */
    @Schema(description = "页码", example = "1")
    private Integer pageNum = 1;

    /**
     * 每页条数（默认10）
     */
    @Schema(description = "每页条数", example = "10")
    private Integer pageSize = 10;
}