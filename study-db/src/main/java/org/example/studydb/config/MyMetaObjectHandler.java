package org.example.studydb.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MyBatis-Plus 公共字段自动填充处理器
 * 配合 BaseEntity 实现 createTime、updateTime、isDeleted 的自动填充
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入数据时自动填充（对应 FieldFill.INSERT）
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 1. 填充创建时间（当前系统时间）
        this.strictInsertFill(
                metaObject,
                "createTime",
                LocalDateTime::now,
                LocalDateTime.class
        );

        // 2. 填充更新时间（插入时与创建时间一致）
        this.strictInsertFill(
                metaObject,
                "updateTime",
                LocalDateTime::now,
                LocalDateTime.class
        );

        // 3. 填充逻辑删除标记（0：未删除，默认值）
        this.strictInsertFill(
                metaObject,
                "isDeleted",
                () -> 0,
                Integer.class
        );
    }

    /**
     * 更新数据时自动填充（对应 FieldFill.INSERT_UPDATE）
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // 1. 填充更新时间（当前系统时间）
        this.strictUpdateFill(
                metaObject,
                "updateTime",
                LocalDateTime::now,
                LocalDateTime.class
        );
    }
}