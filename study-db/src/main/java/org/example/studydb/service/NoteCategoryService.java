package org.example.studydb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.studydb.model.entity.NoteCategory;
import org.example.studydb.model.vo.NoteCategoryVO;

import java.util.List;

/**
 * 笔记分类 Service 接口
 */
public interface NoteCategoryService extends IService<NoteCategory> {

    /**
     * 根据用户 ID 查询笔记分类列表（带笔记数量）
     * @param userId 用户 ID
     * @return 分类列表 VO
     */
    List<NoteCategoryVO> listCategoryVO(Long userId);

    /**
     * 新增笔记分类
     * @param userId 用户 ID
     * @param categoryName 分类名称
     * @return 新增的分类
     */
    NoteCategory addCategory(Long userId, String categoryName);

    /**
     * 修改笔记分类名称
     * @param userId 用户 ID
     * @param categoryId 分类 ID
     * @param newName 新分类名称
     * @return 是否修改成功
     */
    Boolean updateCategoryName(Long userId, Long categoryId, String newName);

    /**
     * 删除笔记分类（需确保分类下无笔记）
     * @param userId 用户 ID
     * @param categoryId 分类 ID
     * @return 是否删除成功
     */
    Boolean deleteCategory(Long userId, Long categoryId);
}