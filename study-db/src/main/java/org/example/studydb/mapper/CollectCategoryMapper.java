package org.example.studydb.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;


import org.apache.ibatis.annotations.Param;
import org.example.studydb.model.entity.CollectCategory;

import java.util.List;

/**
 * 收藏分类Mapper接口（必须继承 BaseMapper，添加正确注解）
 */

public interface CollectCategoryMapper extends BaseMapper<CollectCategory> {

    /**
     * 根据用户ID查询收藏分类列表
     * @param userId 用户ID
     * @return 收藏分类列表
     */
    List<CollectCategory> selectListByUserId(@Param("userId") Long userId);

    /**
     * 统计分类下的收藏数量
     * @param categoryId 分类ID
     * @return 收藏数量
     */
    Integer countCollectByCategoryId(@Param("categoryId") Long categoryId);
}