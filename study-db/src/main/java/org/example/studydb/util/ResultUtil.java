package org.example.studydb.util;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 全局统一响应结果工具类
 * @param <T> 响应数据类型
 */
@Data
@Schema(description = "统一响应结果")
public class ResultUtil<T> {

    /**
     * 响应码：200成功，其他失败
     */
    @Schema(description = "响应码")
    private Integer code;

    /**
     * 响应消息
     */
    @Schema(description = "响应消息")
    private String msg;

    /**
     * 响应数据
     */
    @Schema(description = "响应数据")
    private T data;

    // 私有构造，禁止外部直接实例化
    private ResultUtil() {}

    // 成功响应（无数据，默认消息）
    public static <T> ResultUtil<T> success() {
        return success(null, "操作成功");
    }

    // 成功响应（有数据，默认消息）
    public static <T> ResultUtil<T> success(T data) {
        return success(data, "操作成功");
    }

    // 成功响应（自定义消息+数据，核心重载方法，支撑其他两个success方法）
    public static <T> ResultUtil<T> success(T data, String msg) {
        ResultUtil<T> result = new ResultUtil<>();
        result.setCode(200);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    // 失败响应（自定义码+消息）
    public static <T> ResultUtil<T> fail(Integer code, String msg) {
        ResultUtil<T> result = new ResultUtil<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    // 失败响应（默认500码+自定义消息）
    public static <T> ResultUtil<T> fail(String msg) {
        return fail(500, msg);
    }
}