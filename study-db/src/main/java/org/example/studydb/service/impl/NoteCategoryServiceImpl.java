package org.example.studydb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.example.studydb.exception.BusinessException;
import org.example.studydb.exception.ErrorCode;
import org.example.studydb.mapper.NoteCategoryMapper;
import org.example.studydb.mapper.NoteMapper;
import org.example.studydb.model.entity.Note;
import org.example.studydb.model.entity.NoteCategory;
import org.example.studydb.model.vo.NoteCategoryVO;
import org.example.studydb.service.NoteCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.util.ArrayList;
import java.util.List;

/**
 * 笔记分类 Service 实现类
 */
@Service
public class NoteCategoryServiceImpl extends ServiceImpl<NoteCategoryMapper, NoteCategory> implements NoteCategoryService {

    @Resource
    private NoteCategoryMapper noteCategoryMapper;

    @Resource
    private NoteMapper noteMapper;

    @Override
    public List<NoteCategoryVO> listCategoryVO(Long userId) {
        // 1. 查询用户的所有分类
        List<NoteCategory> categoryList = noteCategoryMapper.selectListByUserId(userId);
        if (categoryList.isEmpty()) {
            return new ArrayList<>();
        }

        // 2. 转换为 VO 并填充笔记数量
        List<NoteCategoryVO> categoryVOList = new ArrayList<>();
        for (NoteCategory category : categoryList) {
            NoteCategoryVO vo = new NoteCategoryVO();
            BeanUtils.copyProperties(category, vo);
            // 统计分类下的笔记数量
            Integer noteCount = noteCategoryMapper.countNoteByCategoryId(category.getId());
            vo.setNoteCount(noteCount);
            categoryVOList.add(vo);
        }
        return categoryVOList;
    }

    @Override
    public NoteCategory addCategory(Long userId, String categoryName) {
        // 1. 参数校验
        if (!StringUtils.hasText(categoryName)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "分类名称不能为空");
        }
        // 校验分类名称是否重复
        LambdaQueryWrapper<NoteCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(NoteCategory::getUserId, userId)
                .eq(NoteCategory::getCategoryName, categoryName);
        if (noteCategoryMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "分类名称已存在");
        }

        // 2. 新增分类
        NoteCategory category = new NoteCategory();
        category.setUserId(userId);
        category.setCategoryName(categoryName);
        category.setSort(0); // 默认排序
        boolean saveSuccess = this.save(category);
        if (!saveSuccess) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "新增分类失败");
        }
        return category;
    }

    @Override
    public Boolean updateCategoryName(Long userId, Long categoryId, String newName) {
        // 1. 参数校验
        if (!StringUtils.hasText(newName)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "新分类名称不能为空");
        }
        // 校验分类是否存在且属于当前用户
        NoteCategory category = this.getById(categoryId);
        if (category == null || !category.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "分类不存在或无操作权限");
        }
        // 校验新名称是否重复
        LambdaQueryWrapper<NoteCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(NoteCategory::getUserId, userId)
                .eq(NoteCategory::getCategoryName, newName)
                .ne(NoteCategory::getId, categoryId);
        if (noteCategoryMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "新分类名称已存在");
        }

        // 2. 更新分类名称
        category.setCategoryName(newName);
        return this.updateById(category);
    }

    @Override
    public Boolean deleteCategory(Long userId, Long categoryId) {
        // 1. 校验分类是否存在且属于当前用户
        NoteCategory category = this.getById(categoryId);
        if (category == null || !category.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "分类不存在或无操作权限");
        }

        // 2. 校验分类下是否有笔记
        LambdaQueryWrapper<Note> noteWrapper = new LambdaQueryWrapper<>();
        noteWrapper.eq(Note::getCategoryId, categoryId);
        if (noteMapper.selectCount(noteWrapper) > 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "分类下存在笔记，无法删除");
        }

        // 3. 逻辑删除分类
        return this.removeById(categoryId);
    }
}