package org.example.studydb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.studydb.model.entity.CollectCategory;
import org.example.studydb.model.vo.CollectCategoryVO;

import java.util.List;

/**
 * 收藏分类 Service 接口
 */
public interface CollectCategoryService extends IService<CollectCategory> {

    /**
     * 根据用户 ID 查询收藏分类列表（带收藏数量）
     * @param userId 用户 ID
     * @return 分类列表 VO
     */
    List<CollectCategoryVO> listCategoryVO(Long userId);

    /**
     * 新增收藏分类
     * @param userId 用户 ID
     * @param categoryName 分类名称
     * @return 新增的分类
     */
    CollectCategory addCategory(Long userId, String categoryName);

    /**
     * 修改收藏分类名称
     * @param userId 用户 ID
     * @param categoryId 分类 ID
     * @param newName 新分类名称
     * @return 是否修改成功
     */
    Boolean updateCategoryName(Long userId, Long categoryId, String newName);

    /**
     * 删除收藏分类（需确保分类下无收藏）
     * @param userId 用户 ID
     * @param categoryId 分类 ID
     * @return 是否删除成功
     */
    Boolean deleteCategory(Long userId, Long categoryId);
}