package org.example.studydb.service;

import org.example.studydb.model.vo.StatCountVO;

/**
 * 统计 Service 接口
 */
public interface StatService {

    /**
     * 获取用户的统计数据
     * @param userId 用户 ID
     * @return 统计数据 VO
     */
    StatCountVO getStatCount(Long userId);
}