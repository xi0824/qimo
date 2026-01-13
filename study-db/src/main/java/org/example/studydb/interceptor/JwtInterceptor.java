package org.example.studydb.interceptor;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.studydb.exception.BusinessException;
import org.example.studydb.exception.ErrorCode;
import org.example.studydb.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


/**
 * JWT登录权限拦截器
 * 拦截需要登录的接口，验证JWT令牌有效性
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    /**
     * 注入JWT工具类
     */
    @Resource
    private JwtUtil jwtUtil;

    /**
     * 接口执行前拦截（核心拦截逻辑）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 从请求头中获取JWT令牌（约定请求头字段：Authorization，格式：Bearer {token}）
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // 无有效令牌，抛出未登录异常
            throw new BusinessException(ErrorCode.USER_NOT_LOGIN, "请先登录后再操作");
        }

        // 2. 提取纯令牌（去除前缀 "Bearer "）
        String token = authHeader.substring(7);

        // 3. 验证令牌有效性并解析
        if (!jwtUtil.validateToken(token)) {
            throw new BusinessException(ErrorCode.USER_TOKEN_EXPIRED, "登录令牌已过期或无效，请重新登录");
        }

        // 4. 解析出userId，存入request.setAttribute（供后续Controller获取）
        Long userId = jwtUtil.getUserIdFromToken(token);
        request.setAttribute("loginUserId", userId);

        // 5. 拦截通过，放行接口
        return true;
    }
}