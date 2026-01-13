package org.example.studydb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.studydb.model.dto.NoteDTO;
import org.example.studydb.model.dto.NoteQueryDTO;
import org.example.studydb.model.entity.Note;
import org.example.studydb.model.vo.NoteVO;
import org.example.studydb.model.vo.PageVO;

/**
 * 笔记 Service 接口
 */
public interface NoteService extends IService<Note> {

    /**
     * 新增笔记
     * @param userId 用户 ID
     * @param noteDTO 笔记请求参数
     * @return 新增的笔记 VO
     */
    NoteVO addNote(Long userId, NoteDTO noteDTO);

    /**
     * 修改笔记
     * @param userId 用户 ID
     * @param noteDTO 笔记请求参数
     * @return 修改后的笔记 VO
     */
    NoteVO updateNote(Long userId, NoteDTO noteDTO);

    /**
     * 删除笔记（逻辑删除）
     * @param userId 用户 ID
     * @param noteId 笔记 ID
     * @return 是否删除成功
     */
    Boolean deleteNote(Long userId, Long noteId);

    /**
     * 根据 ID 查询笔记详情
     * @param userId 用户 ID
     * @param noteId 笔记 ID
     * @return 笔记详情 VO
     */
    NoteVO getNoteDetail(Long userId, Long noteId);

    /**
     * 分页查询笔记列表
     * @param userId 用户 ID
     * @param noteQueryDTO 查询参数
     * @return 分页 VO
     */
    PageVO<NoteVO> pageNote(Long userId, NoteQueryDTO noteQueryDTO);

    /**
     * 增加笔记阅读量
     * @param noteId 笔记 ID
     * @return 是否成功
     */
    Boolean increaseViewCount(Long noteId);
}