package org.example.studydb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.example.studydb.exception.BusinessException;
import org.example.studydb.exception.ErrorCode;
import org.example.studydb.mapper.CollectCategoryMapper;
import org.example.studydb.mapper.CollectMapper;
import org.example.studydb.model.entity.Collect;
import org.example.studydb.model.entity.CollectCategory;
import org.example.studydb.model.vo.CollectCategoryVO;
import org.example.studydb.service.CollectCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.util.ArrayList;
import java.util.List;

/**
 * 收藏分类 Service 实现类
 */
@Service
public class CollectCategoryServiceImpl extends ServiceImpl<CollectCategoryMapper, CollectCategory> implements CollectCategoryService {

    @Resource
    private CollectCategoryMapper collectCategoryMapper;

    @Resource
    private CollectMapper collectMapper;

    @Override
    public List<CollectCategoryVO> listCategoryVO(Long userId) {
        // 1. 查询用户的所有分类
        List<CollectCategory> categoryList = collectCategoryMapper.selectListByUserId(userId);
        if (categoryList.isEmpty()) {
            return new ArrayList<>();
        }

        // 2. 转换为 VO 并填充收藏数量
        List<CollectCategoryVO> categoryVOList = new ArrayList<>();
        for (CollectCategory category : categoryList) {
            CollectCategoryVO vo = new CollectCategoryVO();
            BeanUtils.copyProperties(category, vo);
            // 统计分类下的收藏数量
            Integer collectCount = collectCategoryMapper.countCollectByCategoryId(category.getId());
            vo.setCollectCount(collectCount);
            categoryVOList.add(vo);
        }
        return categoryVOList;
    }

    @Override
    public CollectCategory addCategory(Long userId, String categoryName) {
        // 1. 参数校验（使用优化后的 BusinessException，无标红）
        if (!StringUtils.hasText(categoryName)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "分类名称不能为空");
        }
        // 校验分类名称是否重复
        LambdaQueryWrapper<CollectCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CollectCategory::getUserId, userId)
                .eq(CollectCategory::getCategoryName, categoryName);
        if (collectCategoryMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "分类名称已存在");
        }

        // 2. 新增分类
        CollectCategory category = new CollectCategory();
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
        CollectCategory category = this.getById(categoryId);
        if (category == null || !category.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "分类不存在或无操作权限");
        }
        // 校验新名称是否重复
        LambdaQueryWrapper<CollectCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CollectCategory::getUserId, userId)
                .eq(CollectCategory::getCategoryName, newName)
                .ne(CollectCategory::getId, categoryId);
        if (collectCategoryMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "新分类名称已存在");
        }

        // 2. 更新分类名称
        category.setCategoryName(newName);
        return this.updateById(category);
    }

    @Override
    public Boolean deleteCategory(Long userId, Long categoryId) {
        // 1. 校验分类是否存在且属于当前用户
        CollectCategory category = this.getById(categoryId);
        if (category == null || !category.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "分类不存在或无操作权限");
        }

        // 2. 校验分类下是否有收藏
        LambdaQueryWrapper<Collect> collectWrapper = new LambdaQueryWrapper<>();
        collectWrapper.eq(Collect::getCategoryId, categoryId);
        if (collectMapper.selectCount(collectWrapper) > 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "分类下存在收藏，无法删除");
        }

        // 3. 逻辑删除分类
        return this.removeById(categoryId);
    }
}