package org.example.studydb.util;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 全局统一响应结果
 * @param <T> 响应数据类型
 */
@Data
@Schema(description = "全局统一响应结果")
public class Result<T> {

    /**
     * 响应码（200：成功，其他：失败）
     */
    @Schema(description = "响应码（200-成功，其他-失败）", example = "200")
    private Integer code;

    /**
     * 响应消息
     */
    @Schema(description = "响应消息", example = "操作成功")
    private String msg;

    /**
     * 响应数据
     */
    @Schema(description = "响应数据")
    private T data;

    /**
     * 成功响应（无数据）
     */
    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功", null);
    }

    /**
     * 成功响应（带数据）
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    /**
     * 成功响应（自定义消息+数据）
     */
    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(200, msg, data);
    }

    /**
     * 失败响应（自定义码+消息）
     */
    public static <T> Result<T> fail(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }

    /**
     * 失败响应（自定义消息，默认错误码）
     */
    public static <T> Result<T> fail(String msg) {
        return new Result<>(500, msg, null);
    }

    // 构造方法私有化，通过静态方法创建
    private Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}