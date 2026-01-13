package org.example.studydb.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户表实体
 * 对应数据库表：tb_user
 */
@Data
@TableName("tb_user")
public class User {

    /**
     * 主键ID（自增）- 保持Long类型与JwtUtil一致
     */
    @TableId(type = IdType.AUTO)
    private Long userId; // 改回Long类型，与JwtUtil保持一致

    /**
     * 用户名（唯一，用于登录）
     */
    @TableField("username")
    private String username;

    /**
     * 加密后的密码
     */
    @TableField("password")
    private String password;

    /**
     * 昵称（用于展示）
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 头像地址（可选）- 修正字段映射，匹配数据库avatar_url
     */
    @TableField("avatar_url")
    private String avatar;

    /**
     * 手机号（可选，唯一）
     */
    @TableField("phone")
    private String phone;

    /**
     * 邮箱（可选，唯一）
     */
    @TableField("email")
    private String email;

    /**
     * 注册时间(自动填充)
     */
    @TableField(value = "register_time", fill = FieldFill.INSERT)
    private LocalDateTime registerTime;

    /**
     * 最后登录时间(自动填充)
     */
    @TableField(value = "last_login_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime lastLoginTime;

    /**
     * 逻辑删除标记（0：未删除，1：已删除）
     */
    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;
}
