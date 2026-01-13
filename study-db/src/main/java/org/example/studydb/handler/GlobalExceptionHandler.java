package org.example.studydb.handler;

import org.example.studydb.exception.BusinessException;
import org.example.studydb.util.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * 全局异常处理器
 * 捕获所有Controller层抛出的异常，返回统一响应结果
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义业务异常（BusinessException）
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {
        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * 处理参数校验异常（@Valid 注解触发的 BindException）
     */
    @ExceptionHandler(BindException.class)
    public Result<Void> handleBindException(BindException e) {
        // 提取所有字段的错误信息，拼接为字符串
        String errorMsg = e.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("；"));
        return Result.fail(400, "参数校验失败：" + errorMsg);
    }

    /**
     * 处理其他所有未捕获的系统异常（兜底处理）
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        // 生产环境建议隐藏具体异常信息，只返回通用提示
        e.printStackTrace();
        return Result.fail(500, "系统内部异常，请稍后重试");
    }
}