package org.example.studydb.model.vo;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 笔记分类响应VO
 */
@Data
@Schema(description = "笔记分类响应数据")
public class NoteCategoryVO {

    /**
     * 分类ID
     */
    @Schema(description = "分类ID", example = "1")
    private Long id;

    /**
     * 分类名称
     */
    @Schema(description = "分类名称", example = "Java学习")
    private String categoryName;

    /**
     * 分类排序
     */
    @Schema(description = "分类排序", example = "1")
    private Integer sort;

    /**
     * 该分类下笔记数量
     */
    @Schema(description = "笔记数量", example = "10")
    private Integer noteCount;
}