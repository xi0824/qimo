package org.example.studydb.model.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * 待办查询筛选请求DTO
 */
@Data
@Schema(description = "待办查询筛选请求参数")
public class TodoQueryDTO {

    /**
     * 优先级（可选，1：低，2：中，3：高）
     */
    @Schema(description = "优先级（1-低，2-中，3-高）", example = "2")
    private Integer priority;

    /**
     * 状态（可选，0：未开始，1：进行中，2：已完成）
     */
    @Schema(description = "状态（0-未开始，1-进行中，2-已完成）", example = "0")
    private Integer status;

    /**
     * 截止时间开始区间（可选）
     */
    @Schema(description = "截止时间开始区间", example = "2026-01-01 00:00:00")
    private LocalDateTime deadlineStart;

    /**
     * 截止时间结束区间（可选）
     */
    @Schema(description = "截止时间结束区间", example = "2026-01-31 23:59:59")
    private LocalDateTime deadlineEnd;

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