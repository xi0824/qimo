package org.example.studydb.util;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * 密码加密/验证工具（MD5+盐值）
 */
public class PasswordUtil {

    // 盐值（生产环境建议配置到上面的 application.yml 自定义配置中）
    private static final String SALT = "study_manager_2026";

    /**
     * 密码加密
     * @param password 原始密码
     * @return 加密后的密码
     */
    public static String encryptPassword(String password) {
        Digester md5 = new Digester(DigestAlgorithm.MD5);
        // 拼接盐值后加密
        return md5.digestHex(password + SALT);
    }

    /**
     * 验证密码
     * @param rawPassword 原始密码
     * @param encryptPassword 加密后的密码
     * @return 是否匹配
     */
    public static boolean verifyPassword(String rawPassword, String encryptPassword) {
        return encryptPassword(rawPassword).equals(encryptPassword);
    }
}