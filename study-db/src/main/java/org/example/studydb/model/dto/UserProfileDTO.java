package org.example.studydb.model.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 用户个人信息修改请求DTO
 */
@Data
@Schema(description = "用户个人信息修改请求参数")
public class UserProfileDTO {

    /**
     * 昵称
     */
    @Size(max = 20, message = "昵称长度不能超过20")
    @Schema(description = "昵称", example = "张三")
    private String nickname;

    /**
     * 头像地址（可选，前端上传后返回的地址）
     */
    @Schema(description = "头像地址", example = "https://example.com/avatar.jpg")
    private String avatar;

    /**
     * 手机号（可选）
     */
    @Size(max = 11, message = "手机号长度不能超过11")
    @Schema(description = "手机号", example = "13800138000")
    private String phone;

    /**
     * 邮箱（可选）
     */
    @Size(max = 50, message = "邮箱长度不能超过50")
    @Schema(description = "邮箱", example = "zhangsan@example.com")
    private String email;
}