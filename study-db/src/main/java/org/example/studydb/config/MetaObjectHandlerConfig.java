package org.example.studydb.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

/**
 * MyBatis-Plus 字段自动填充配置
 * 实现创建时间、更新时间的自动填充
 */
@Component
public class MetaObjectHandlerConfig implements MetaObjectHandler {

    /**
     * 插入操作时填充字段
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 原有填充逻辑：保留不动，确保其他实体（如Collect）的功能正常
        this.strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "collectTime", LocalDateTime::now, LocalDateTime.class);

        // 新增：填充User实体的registerTime和lastLoginTime（插入时赋值）
        this.strictInsertFill(metaObject, "registerTime", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "lastLoginTime", LocalDateTime::now, LocalDateTime.class);
    }

    /**
     * 更新操作时填充字段
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // 原有填充逻辑：保留不动
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);

        // 新增：填充User实体的lastLoginTime（更新时赋值为当前时间）
        this.strictUpdateFill(metaObject, "lastLoginTime", LocalDateTime::now, LocalDateTime.class);
    }
}