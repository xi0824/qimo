package org.example.studydb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.studydb.model.entity.Tomato;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 番茄钟表Mapper接口
 */

public interface TomatoMapper extends BaseMapper<Tomato> {

    /**
     * 按用户ID查询番茄钟列表（近7天）
     * @param userId 用户ID
     * @return 番茄钟列表
     */
    List<Tomato> selectListByUserIdRecent7Days(@Param("userId") Long userId);
}