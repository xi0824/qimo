package org.example.studydb.model.vo;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * 用户信息响应VO
 */
@Data
@Schema(description = "用户信息响应数据")
public class UserVO {

    /**
     * 主键ID
     */
    @Schema(description = "用户ID", example = "1")
    private Long id;

    /**
     * 用户名
     */
    @Schema(description = "用户名", example = "zhangsan")
    private String username;

    /**
     * 昵称
     */
    @Schema(description = "昵称", example = "张三")
    private String nickname;

    /**
     * 头像地址
     */
    @Schema(description = "头像地址", example = "https://example.com/avatar.jpg")
    private String avatar;

    /**
     * 手机号
     */
    @Schema(description = "手机号", example = "13800138000")
    private String phone;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱", example = "zhangsan@example.com")
    private String email;

    /**
     * 创建时间
     */
    @Schema(description = "注册时间", example = "2026-01-01 10:00:00")
    private LocalDateTime createTime;
}