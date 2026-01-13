package org.example.studydb.model.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 收藏查询筛选请求DTO
 */
@Data
@Schema(description = "收藏查询筛选请求参数")
public class CollectQueryDTO {

    /**
     * 分类ID（可选，用于筛选指定分类的收藏）
     */
    @Schema(description = "分类ID", example = "1")
    private Long categoryId;

    /**
     * 标题/描述关键词（可选，用于模糊查询）
     */
    @Schema(description = "标题/描述关键词", example = "GitHub")
    private String keyword;

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