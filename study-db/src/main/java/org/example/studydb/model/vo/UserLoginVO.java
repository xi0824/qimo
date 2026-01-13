package org.example.studydb.model.vo;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 用户登录响应VO
 */
@Data
@Schema(description = "用户登录响应数据")
public class UserLoginVO {

    /**
     * 登录令牌（JWT）
     */
    @Schema(description = "JWT令牌", example = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsInVzZXJuYW1lIjoiemhhbmdzYW4iLCJleHAiOjE3MTExMzgwMDAsImlhdCI6MTcxMTEzMDgwMH0.xxxx")
    private String token;

    /**
     * 用户基本信息
     */
    @Schema(description = "用户基本信息")
    private UserVO userInfo;
}