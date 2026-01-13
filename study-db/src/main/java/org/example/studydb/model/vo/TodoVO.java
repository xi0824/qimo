package org.example.studydb.model.vo;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * 待办响应VO
 */
@Data
@Schema(description = "待办响应数据")
public class TodoVO {

    /**
     * 待办ID
     */
    @Schema(description = "待办ID", example = "1")
    private Long id;

    /**
     * 待办标题
     */
    @Schema(description = "待办标题", example = "完成Spring Boot项目开发")
    private String title;

    /**
     * 待办描述
     */
    @Schema(description = "待办描述", example = "完成学习管理系统的后端接口开发")
    private String description;

    /**
     * 优先级（1：低，2：中，3：高）
     */
    @Schema(description = "优先级（1-低，2-中，3-高）", example = "2")
    private Integer priority;

    /**
     * 优先级名称
     */
    @Schema(description = "优先级名称", example = "中")
    private String priorityName;

    /**
     * 状态（0：未开始，1：进行中，2：已完成）
     */
    @Schema(description = "状态（0-未开始，1-进行中，2-已完成）", example = "0")
    private Integer status;

    /**
     * 状态名称
     */
    @Schema(description = "状态名称", example = "未开始")
    private String statusName;

    /**
     * 截止时间
     */
    @Schema(description = "截止时间", example = "2026-02-01 23:59:59")
    private LocalDateTime deadline;

    /**
     * 完成时间
     */
    @Schema(description = "完成时间", example = "2026-01-30 18:00:00")
    private LocalDateTime finishTime;

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