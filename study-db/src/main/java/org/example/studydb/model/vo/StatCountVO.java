package org.example.studydb.model.vo;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 统计数据卡片VO
 */
@Data
@Schema(description = "统计数据卡片响应数据")
public class StatCountVO {

    /**
     * 笔记总数
     */
    @Schema(description = "笔记总数", example = "50")
    private Long noteTotal;

    /**
     * 收藏总数
     */
    @Schema(description = "收藏总数", example = "20")
    private Long collectTotal;

    /**
     * 待办总数
     */
    @Schema(description = "待办总数", example = "15")
    private Long todoTotal;

    /**
     * 待办完成数
     */
    @Schema(description = "待办完成数", example = "10")
    private Long todoFinishTotal;

    /**
     * 待办完成率（保留2位小数）
     */
    @Schema(description = "待办完成率", example = "66.67")
    private Double todoFinishRate;
}