package org.example.studydb.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 用户登录请求DTO
 */
@Data
@Schema(description = "用户登录请求参数")
public class UserLoginDTO {

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
}