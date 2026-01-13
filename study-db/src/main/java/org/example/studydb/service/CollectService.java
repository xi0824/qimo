package org.example.studydb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.studydb.model.dto.CollectDTO;
import org.example.studydb.model.dto.CollectQueryDTO;
import org.example.studydb.model.entity.Collect;
import org.example.studydb.model.vo.CollectVO;
import org.example.studydb.model.vo.PageVO;

/**
 * 收藏 Service 接口
 */
public interface CollectService extends IService<Collect> {

    /**
     * 新增收藏
     * @param userId 用户 ID
     * @param collectDTO 收藏请求参数
     * @return 新增的收藏 VO
     */
    CollectVO addCollect(Long userId, CollectDTO collectDTO);

    /**
     * 修改收藏
     * @param userId 用户 ID
     * @param collectDTO 收藏请求参数
     * @return 修改后的收藏 VO
     */
    CollectVO updateCollect(Long userId, CollectDTO collectDTO);

    /**
     * 删除收藏（逻辑删除）
     * @param userId 用户 ID
     * @param collectId 收藏 ID
     * @return 是否删除成功
     */
    Boolean deleteCollect(Long userId, Long collectId);

    /**
     * 根据 ID 查询收藏详情
     * @param userId 用户 ID
     * @param collectId 收藏 ID
     * @return 收藏详情 VO
     */
    CollectVO getCollectDetail(Long userId, Long collectId);

    /**
     * 分页查询收藏列表
     * @param userId 用户 ID
     * @param collectQueryDTO 查询参数
     * @return 分页 VO
     */
    PageVO<CollectVO> pageCollect(Long userId, CollectQueryDTO collectQueryDTO);

    /**
     * 统计用户收藏总数
     * @param userId 用户 ID
     * @return 收藏总数
     */
    Long countTotalByUserId(Long userId);
}