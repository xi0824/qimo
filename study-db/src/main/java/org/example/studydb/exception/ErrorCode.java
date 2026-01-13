package org.example.studydb.exception;

import lombok.Getter;

/**
 * 错误码枚举
 */
@Getter
public enum ErrorCode {

    // 系统通用错误
    SYSTEM_ERROR(500, "系统内部异常，请稍后重试"),
    PARAM_ERROR(400, "请求参数错误"),

    // 用户相关错误
    USER_NOT_EXIST(401, "用户不存在"),
    USER_PASSWORD_ERROR(402, "用户密码错误"),
    USER_NOT_LOGIN(403, "用户未登录"),
    USER_TOKEN_EXPIRED(404, "登录令牌已过期或无效"),

    // 业务相关错误（笔记、收藏、待办）
    BUSINESS_ERROR(405, "业务操作失败");

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误信息
     */
    private final String msg;

    ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}