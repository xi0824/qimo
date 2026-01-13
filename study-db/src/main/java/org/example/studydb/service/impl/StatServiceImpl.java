package org.example.studydb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.example.studydb.model.entity.Note;
import org.example.studydb.model.vo.StatCountVO;
import org.example.studydb.service.NoteService;
import org.example.studydb.service.CollectService;
import org.example.studydb.service.StatService;
import org.example.studydb.service.TodoService;
import org.springframework.stereotype.Service;


/**
 * 统计 Service 实现类
 */
@Service
public class StatServiceImpl implements StatService {

    @Resource
    private NoteService noteService;

    @Resource
    private CollectService collectService;

    @Resource
    private TodoService todoService;

    @Override
    public StatCountVO getStatCount(Long userId) {
        // 1. 查询各模块数据（修正：明确 LambdaQueryWrapper<Note> 泛型类型）
        Long noteTotal = noteService.count(new LambdaQueryWrapper<Note>().eq(Note::getUserId, userId));

        Long collectTotal = collectService.countTotalByUserId(userId);
        Long todoTotal = todoService.countTotalByUserId(userId);
        Long todoFinishTotal = todoService.countFinishTotalByUserId(userId);

        // 2. 计算完成率（避免除以 0）
        Double todoFinishRate = todoTotal == 0 ? 0.0 : (todoFinishTotal * 100.0) / todoTotal;
        // 保留两位小数
        todoFinishRate = Math.round(todoFinishRate * 100.0) / 100.0;

        // 3. 构建统计 VO
        StatCountVO vo = new StatCountVO();
        vo.setNoteTotal(noteTotal);
        vo.setCollectTotal(collectTotal);
        vo.setTodoTotal(todoTotal);
        vo.setTodoFinishTotal(todoFinishTotal);
        vo.setTodoFinishRate(todoFinishRate);

        return vo;
    }
}