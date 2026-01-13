package org.example.studydb.model.vo;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * 收藏响应VO
 */
@Data
@Schema(description = "收藏响应数据")
public class CollectVO {

    /**
     * 收藏ID
     */
    @Schema(description = "收藏ID", example = "1")
    private Long id;

    /**
     * 收藏标题
     */
    @Schema(description = "收藏标题", example = "GitHub 官网")
    private String title;

    /**
     * 网页链接
     */
    @Schema(description = "网页链接", example = "https://github.com/")
    private String url;

    /**
     * 描述信息
     */
    @Schema(description = "描述信息", example = "全球最大的开源代码托管平台")
    private String description;

    /**
     * 分类ID
     */
    @Schema(description = "分类ID", example = "1")
    private Long categoryId;

    /**
     * 分类名称
     */
    @Schema(description = "分类名称", example = "技术网站")
    private String categoryName;

    /**
     * 收藏时间
     */
    @Schema(description = "收藏时间", example = "2026-01-01 10:00:00")
    private LocalDateTime collectTime;
}