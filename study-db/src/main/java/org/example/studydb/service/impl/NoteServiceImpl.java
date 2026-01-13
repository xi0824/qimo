package org.example.studydb.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.example.studydb.exception.BusinessException;
import org.example.studydb.exception.ErrorCode;
import org.example.studydb.mapper.NoteCategoryMapper;
import org.example.studydb.mapper.NoteMapper;
import org.example.studydb.model.dto.NoteDTO;
import org.example.studydb.model.dto.NoteQueryDTO;
import org.example.studydb.model.entity.Note;
import org.example.studydb.model.entity.NoteCategory;
import org.example.studydb.model.vo.NoteVO;
import org.example.studydb.model.vo.PageVO;
import org.example.studydb.service.NoteService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 笔记 Service 实现类
 */
@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements NoteService {

    @Resource
    private NoteMapper noteMapper;

    @Resource
    private NoteCategoryMapper noteCategoryMapper;

    @Override
    public NoteVO addNote(Long userId, NoteDTO noteDTO) {
        // 1. 校验分类是否存在且属于当前用户
        NoteCategory category = noteCategoryMapper.selectById(noteDTO.getCategoryId());
        if (category == null || !category.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "笔记分类不存在或无操作权限");
        }

        // 2. 构建笔记实体
        Note note = new Note();
        BeanUtils.copyProperties(noteDTO, note);
        note.setUserId(userId);
        note.setViewCount(0); // 初始阅读量为0

        // 3. 保存笔记
        boolean saveSuccess = this.save(note);
        if (!saveSuccess) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "新增笔记失败");
        }

        // 4. 转换为 VO 并返回
        return this.convertToVO(note);
    }

    @Override
    public NoteVO updateNote(Long userId, NoteDTO noteDTO) {
        // 1. 校验笔记是否存在且属于当前用户
        Long noteId = noteDTO.getId();
        Note note = this.getById(noteId);
        if (note == null || !note.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "笔记不存在或无操作权限");
        }

        // 2. 校验分类是否存在且属于当前用户
        NoteCategory category = noteCategoryMapper.selectById(noteDTO.getCategoryId());
        if (category == null || !category.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "笔记分类不存在或无操作权限");
        }

        // 3. 更新笔记信息
        BeanUtils.copyProperties(noteDTO, note);
        boolean updateSuccess = this.updateById(note);
        if (!updateSuccess) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "修改笔记失败");
        }

        // 4. 转换为 VO 并返回
        return this.convertToVO(note);
    }

    @Override
    public Boolean deleteNote(Long userId, Long noteId) {
        // 校验笔记是否存在且属于当前用户
        Note note = this.getById(noteId);
        if (note == null || !note.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "笔记不存在或无操作权限");
        }
        // 逻辑删除笔记
        return this.removeById(noteId);
    }

    @Override
    public NoteVO getNoteDetail(Long userId, Long noteId) {
        // 1. 校验笔记是否存在且属于当前用户
        Note note = this.getById(noteId);
        if (note == null || !note.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "笔记不存在或无操作权限");
        }
        // 2. 转换为 VO 并返回
        return this.convertToVO(note);
    }

    @Override
    public PageVO<NoteVO> pageNote(Long userId, NoteQueryDTO noteQueryDTO) {
        // 1. 构建分页对象
        Page<Note> page = new Page<>(noteQueryDTO.getPageNum(), noteQueryDTO.getPageSize());

        // 2. 调用 Mapper 分页查询
        // 2. 调用 Mapper 分页查询（修正：用 IPage<Note> 接收返回值，匹配方法返回类型）
        IPage<Note> noteIPage = noteMapper.selectPageByCondition(
                page,
                userId,
                noteQueryDTO.getCategoryId(),
                noteQueryDTO.getTitleKeyword(),
                noteQueryDTO.getTagKeyword()
        );

        // 3. 转换为 VO 分页结果（直接使用 noteIPage 构建 Page<NoteVO>，无需额外修改）
        Page<NoteVO> voPage = new Page<>(noteIPage.getCurrent(), noteIPage.getSize(), noteIPage.getTotal());
        voPage.setRecords(noteIPage.getRecords().stream().map(this::convertToVO).toList());

        // 4. 封装为通用分页 VO
        return PageVO.build(voPage);
    }

    @Override
    public Boolean increaseViewCount(Long noteId) {
        Note note = this.getById(noteId);
        if (note == null) {
            return false;
        }
        note.setViewCount(note.getViewCount() + 1);
        return this.updateById(note);
    }

    /**
     * 私有方法：将 Note 实体转换为 NoteVO
     */
    private NoteVO convertToVO(Note note) {
        NoteVO vo = new NoteVO();
        BeanUtils.copyProperties(note, vo);
        // 填充分类名称
        NoteCategory category = noteCategoryMapper.selectById(note.getCategoryId());
        if (category != null) {
            vo.setCategoryName(category.getCategoryName());
        }
        return vo;
    }
}