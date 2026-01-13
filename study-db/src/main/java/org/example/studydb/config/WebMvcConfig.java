package org.example.studydb.config;

import jakarta.annotation.Resource;
import org.example.studydb.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



/**
 * Web MVC配置类
 * 注册JWT拦截器，配置拦截规则
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private JwtInterceptor jwtInterceptor;

    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                // 1. 拦截所有/api开头的接口（系统核心接口）
                .addPathPatterns("/api/**")
                // 2. 放行登录、注册接口（无需登录即可访问）
                .excludePathPatterns(
                        "/api/user/register",
                        "/api/user/login"
                );
    }
}