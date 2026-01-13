package org.example.studydb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.studydb.model.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * 用户表Mapper接口
 * 继承BaseMapper获得通用CRUD方法
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    User selectByUsername(String username);
}
