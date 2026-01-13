package org.example.studydb.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.example.studydb.exception.BusinessException;
import org.example.studydb.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类（生成令牌、解析令牌、验证令牌）
 */
@Component
public class JwtUtil {

    /**
     * JWT密钥（从配置文件读取）
     */
    @Value("${custom.jwt.secret}")
    private String secret;

    /**
     * JWT过期时间（毫秒，从配置文件读取，默认2小时）
     */
    @Value("${custom.jwt.expire}")
    private Long expire;

    /**
     * 生成JWT令牌
     * @param userId 用户ID
     * @param username 用户名
     * @return JWT令牌
     */
    public String generateToken(Long userId, String username) {
        // 1. 构建密钥
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        // 2. 构建自定义声明
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("username", username);

        // 3. 生成令牌
        return Jwts.builder()
                .setClaims(claims) // 自定义声明
                .setSubject(username) // 主题（用户名）
                .setIssuedAt(new Date()) // 签发时间
                .setExpiration(new Date(System.currentTimeMillis() + expire)) // 过期时间
                .signWith(key) // 签名密钥
                .compact();
    }

    /**
     * 解析JWT令牌，获取声明信息
     * @param token JWT令牌
     * @return 声明信息
     */
    public Claims parseToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            // 解析失败（令牌过期、非法令牌等）
            throw new BusinessException(ErrorCode.USER_TOKEN_EXPIRED);
        }
    }

    /**
     * 从令牌中获取用户ID
     * @param token JWT令牌
     * @return 用户ID
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = parseToken(token);
        return Long.valueOf(claims.get("userId").toString());
    }

    /**
     * 从令牌中获取用户名
     * @param token JWT令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.get("username", String.class);
    }

    /**
     * 验证令牌是否有效
     * @param token JWT令牌
     * @return 是否有效（true：有效，false：无效）
     */
    public Boolean validateToken(String token) {
        try {
            Claims claims = parseToken(token);
            // 检查是否过期
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}