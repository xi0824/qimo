package org.example.studydb.exception;

import lombok.Getter;

/**
 * 自定义业务异常
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 构造方法1：自定义码+消息（原有，保持兼容）
     */
    public BusinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    /**
     * 构造方法2：错误码枚举（推荐，简洁易用，解决标红问题）
     */
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }

    /**
     * 构造方法3：错误码枚举+自定义消息（更灵活，补充）
     */
    public BusinessException(ErrorCode errorCode, String customMsg) {
        super(customMsg);
        this.code = errorCode.getCode();
    }
}