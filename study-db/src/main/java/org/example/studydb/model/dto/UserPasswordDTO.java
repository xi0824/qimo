package org.example.studydb.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 用户密码修改请求DTO
 */
@Data
@Schema(description = "用户密码修改请求参数")
public class UserPasswordDTO {

    /**
     * 原密码
     */
    @NotBlank(message = "原密码不能为空")
    @Schema(description = "原密码", example = "123456")
    private String oldPassword;

    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空")
    @Size(min = 6, max = 32, message = "新密码长度必须在6-32之间")
    @Schema(description = "新密码", example = "654321")
    private String newPassword;

    /**
     * 确认新密码
     */
    @NotBlank(message = "确认新密码不能为空")
    @Schema(description = "确认新密码", example = "654321")
    private String confirmNewPassword;
}