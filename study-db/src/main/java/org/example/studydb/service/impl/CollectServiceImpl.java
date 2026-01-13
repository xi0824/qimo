package org.example.studydb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.example.studydb.exception.BusinessException;
import org.example.studydb.exception.ErrorCode;
import org.example.studydb.mapper.CollectCategoryMapper;
import org.example.studydb.mapper.CollectMapper;
import org.example.studydb.model.dto.CollectDTO;
import org.example.studydb.model.dto.CollectQueryDTO;
import org.example.studydb.model.entity.Collect;
import org.example.studydb.model.entity.CollectCategory;
import org.example.studydb.model.vo.CollectVO;
import org.example.studydb.model.vo.PageVO;
import org.example.studydb.service.CollectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;



/**
 * 收藏 Service 实现类
 */
@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {

    @Resource
    private CollectMapper collectMapper;

    @Resource
    private CollectCategoryMapper collectCategoryMapper;

    @Override
    public CollectVO addCollect(Long userId, CollectDTO collectDTO) {
        // 1. 校验分类是否存在且属于当前用户
        CollectCategory category = collectCategoryMapper.selectById(collectDTO.getCategoryId());
        if (category == null || !category.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "收藏分类不存在或无操作权限");
        }

        // 2. 校验链接是否重复（同一用户下链接唯一）
        LambdaQueryWrapper<Collect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Collect::getUserId, userId)
                .eq(Collect::getUrl, collectDTO.getUrl());
        if (collectMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "该链接已收藏，无需重复添加");
        }

        // 3. 构建收藏实体
        Collect collect = new Collect();
        BeanUtils.copyProperties(collectDTO, collect);
        collect.setUserId(userId);

        // 4. 保存收藏
        boolean saveSuccess = this.save(collect);
        if (!saveSuccess) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "新增收藏失败");
        }

        // 5. 转换为 VO 并返回
        return this.convertToVO(collect);
    }

    @Override
    public CollectVO updateCollect(Long userId, CollectDTO collectDTO) {
        // 1. 校验收藏是否存在且属于当前用户
        Long collectId = collectDTO.getId();
        Collect collect = this.getById(collectId);
        if (collect == null || !collect.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "收藏不存在或无操作权限");
        }

        // 2. 校验分类是否存在且属于当前用户
        CollectCategory category = collectCategoryMapper.selectById(collectDTO.getCategoryId());
        if (category == null || !category.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "收藏分类不存在或无操作权限");
        }

        // 3. 校验链接是否重复（排除当前收藏本身）
        LambdaQueryWrapper<Collect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Collect::getUserId, userId)
                .eq(Collect::getUrl, collectDTO.getUrl())
                .ne(Collect::getId, collectId);
        if (collectMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "该链接已收藏，无需重复添加");
        }

        // 4. 更新收藏信息
        BeanUtils.copyProperties(collectDTO, collect);
        boolean updateSuccess = this.updateById(collect);
        if (!updateSuccess) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "修改收藏失败");
        }

        // 5. 转换为 VO 并返回
        return this.convertToVO(collect);
    }

    @Override
    public Boolean deleteCollect(Long userId, Long collectId) {
        // 校验收藏是否存在且属于当前用户
        Collect collect = this.getById(collectId);
        if (collect == null || !collect.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "收藏不存在或无操作权限");
        }
        // 逻辑删除收藏
        return this.removeById(collectId);
    }

    @Override
    public CollectVO getCollectDetail(Long userId, Long collectId) {
        // 1. 校验收藏是否存在且属于当前用户
        Collect collect = this.getById(collectId);
        if (collect == null || !collect.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "收藏不存在或无操作权限");
        }
        // 2. 转换为 VO 并返回
        return this.convertToVO(collect);
    }

    @Override
    public PageVO<CollectVO> pageCollect(Long userId, CollectQueryDTO collectQueryDTO) {
        // 1. 构建分页对象（使用 Page 实例，符合 MyBatis-Plus 用法）
        Page<Collect> page = new Page<>(collectQueryDTO.getPageNum(), collectQueryDTO.getPageSize());

        // 2. 调用 Mapper 分页查询（使用 IPage 接收，规避类型匹配问题，方案1适配）
        IPage<Collect> collectIPage = collectMapper.selectPageByCondition(
                page,
                userId,
                collectQueryDTO.getCategoryId(),
                collectQueryDTO.getKeyword()
        );

        // 3. 转换为 VO 分页结果
        Page<CollectVO> voPage = new Page<>(collectIPage.getCurrent(), collectIPage.getSize(), collectIPage.getTotal());
        voPage.setRecords(collectIPage.getRecords().stream().map(this::convertToVO).toList());

        // 4. 封装为通用分页 VO
        return PageVO.build(voPage);
    }

    @Override
    public Long countTotalByUserId(Long userId) {
        return collectMapper.countTotalByUserId(userId);
    }

    /**
     * 私有方法：将 Collect 实体转换为 CollectVO
     */
    private CollectVO convertToVO(Collect collect) {
        CollectVO vo = new CollectVO();
        BeanUtils.copyProperties(collect, vo);
        // 填充分类名称
        CollectCategory category = collectCategoryMapper.selectById(collect.getCategoryId());
        if (category != null) {
            vo.setCategoryName(category.getCategoryName());
        }
        return vo;
    }
}