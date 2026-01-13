package org.example.studydb.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 用户注册请求DTO
 */
@Data
@Schema(description = "用户注册请求参数")
public class UserRegisterDTO {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 20, message = "用户名长度必须在3-20之间")
    @Schema(description = "用户名", example = "zhangsan")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 32, message = "密码长度必须在6-32之间")
    @Schema(description = "密码", example = "123456")
    private String password;

    /**
     * 确认密码
     */
    @NotBlank(message = "确认密码不能为空")
    @Schema(description = "确认密码", example = "123456")
    private String confirmPassword;

    /**
     * 昵称（可选，默认与用户名一致）
     */
    @Size(max = 20, message = "昵称长度不能超过20")
    @Schema(description = "昵称", example = "张三")
    private String nickname;
}