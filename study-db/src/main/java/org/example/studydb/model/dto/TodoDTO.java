package org.example.studydb.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * 待办新增/编辑请求DTO
 */
@Data
@Schema(description = "待办新增/编辑请求参数")
public class TodoDTO {

    /**
     * 待办ID（编辑时必填，新增时为null）
     */
    @Schema(description = "待办ID（编辑必填）", example = "1")
    private Long id;

    /**
     * 待办标题
     */
    @NotBlank(message = "待办标题不能为空")
    @Size(max = 100, message = "待办标题长度不能超过100")
    @Schema(description = "待办标题", example = "完成Spring Boot项目开发")
    private String title;

    /**
     * 待办描述（可选）
     */
    @Size(max = 500, message = "待办描述长度不能超过500")
    @Schema(description = "待办描述", example = "完成学习管理系统的后端接口开发")
    private String description;

    /**
     * 优先级（1：低，2：中，3：高）
     */
    @NotNull(message = "优先级不能为空")
    @Schema(description = "优先级（1-低，2-中，3-高）", example = "2")
    private Integer priority;

    /**
     * 状态（0：未开始，1：进行中，2：已完成）
     */
    @Schema(description = "状态（0-未开始，1-进行中，2-已完成）", example = "0")
    private Integer status = 0;

    /**
     * 截止时间（可选）
     */
    @Schema(description = "截止时间", example = "2026-02-01 23:59:59")
    private LocalDateTime deadline;
}