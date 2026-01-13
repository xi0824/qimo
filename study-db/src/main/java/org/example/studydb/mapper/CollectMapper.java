package org.example.studydb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.studydb.model.entity.Collect;
import org.apache.ibatis.annotations.Param;

/**
 * 收藏表Mapper接口
 */

public interface CollectMapper extends BaseMapper<Collect> {

    /**
     * 按用户ID+多条件分页查询收藏列表
     * @param page 分页对象
     * @param userId 用户ID
     * @param categoryId 分类ID（可选，可为null）
     * @param keyword 标题/描述关键词（可选，可为null）
     * @return 分页收藏列表
     */
    IPage<Collect> selectPageByCondition(IPage<Collect> page,
                                         @Param("userId") Long userId,
                                         @Param("categoryId") Long categoryId,
                                         @Param("keyword") String keyword);

    /**
     * 统计用户收藏总数
     * @param userId 用户ID
     * @return 收藏总数
     */
    Long countTotalByUserId(@Param("userId") Long userId);
}