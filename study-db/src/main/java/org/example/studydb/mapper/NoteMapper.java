package org.example.studydb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.studydb.model.entity.Note;
import org.apache.ibatis.annotations.Param;

/**
 * 笔记表Mapper接口
 */

public interface NoteMapper extends BaseMapper<Note> {

    /**
     * 按用户ID+多条件分页查询笔记列表
     * @param page 分页对象（传入分页参数，返回分页结果）
     * @param userId 用户ID
     * @param categoryId 分类ID（可选，可为null）
     * @param titleKeyword 标题关键词（可选，可为null）
     * @param tagKeyword 标签关键词（可选，可为null）
     * @return 分页笔记列表
     */
    IPage<Note> selectPageByCondition(IPage<Note> page,
                                      @Param("userId") Long userId,
                                      @Param("categoryId") Long categoryId,
                                      @Param("titleKeyword") String titleKeyword,
                                      @Param("tagKeyword") String tagKeyword);

    /**
     * 统计用户笔记总数
     * @param userId 用户ID
     * @return 笔记总数
     */
    Long countTotalByUserId(@Param("userId") Long userId);
}