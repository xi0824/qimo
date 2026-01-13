package org.example.studydb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置类
 * 解决前后端分离架构下的跨域请求问题
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * 配置跨域规则
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 允许所有接口的跨域请求
        registry.addMapping("/**")
                // 1. 允许的来源（前端域名，* 表示允许所有，生产环境建议指定具体域名）
                .allowedOriginPatterns("*")
                // 2. 允许的请求方法（GET、POST、PUT、DELETE等）
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 3. 允许的请求头（包括自定义请求头，如Authorization）
                .allowedHeaders("*")
                // 4. 是否允许携带Cookie（前后端认证需要时开启）
                .allowCredentials(true)
                // 5. 预检请求的有效期（秒，默认30分钟，避免频繁发送预检请求）
                .maxAge(3600);
    }
}