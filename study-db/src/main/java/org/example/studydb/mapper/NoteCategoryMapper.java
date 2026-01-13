package org.example.studydb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.studydb.model.entity.NoteCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 笔记分类表Mapper接口
 */

public interface NoteCategoryMapper extends BaseMapper<NoteCategory> {

    /**
     * 按用户ID查询笔记分类列表（未删除）
     * @param userId 用户ID
     * @return 笔记分类列表
     */
    List<NoteCategory> selectListByUserId(@Param("userId") Long userId);

    /**
     * 统计分类下笔记数量
     * @param categoryId 分类ID
     * @return 笔记数量
     */
    Integer countNoteByCategoryId(@Param("categoryId") Long categoryId);
}