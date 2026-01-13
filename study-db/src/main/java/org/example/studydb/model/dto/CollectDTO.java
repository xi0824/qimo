package org.example.studydb.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 收藏新增/编辑请求DTO
 */
@Data
@Schema(description = "收藏新增/编辑请求参数")
public class CollectDTO {

    /**
     * 收藏ID（编辑时必填，新增时为null）
     */
    @Schema(description = "收藏ID（编辑必填）", example = "1")
    private Long id;

    /**
     * 收藏标题
     */
    @NotBlank(message = "收藏标题不能为空")
    @Size(max = 100, message = "收藏标题长度不能超过100")
    @Schema(description = "收藏标题", example = "GitHub 官网")
    private String title;

    /**
     * 网页链接
     */
    @NotBlank(message = "网页链接不能为空")
    @Size(max = 500, message = "网页链接长度不能超过500")
    @Schema(description = "网页链接", example = "https://github.com/")
    private String url;

    /**
     * 描述信息（可选）
     */
    @Size(max = 500, message = "描述信息长度不能超过500")
    @Schema(description = "描述信息", example = "全球最大的开源代码托管平台")
    private String description;

    /**
     * 分类ID
     */
    @NotNull(message = "收藏分类不能为空")
    @Schema(description = "分类ID", example = "1")
    private Long categoryId;
}