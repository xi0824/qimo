package org.example.studydb.model.vo;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/**
 * 通用分页响应VO
 * @param <T> 分页数据类型
 */
@Data
@Schema(description = "通用分页响应数据")
public class PageVO<T> {

    /**
     * 总记录数
     */
    @Schema(description = "总记录数", example = "100")
    private Long total;

    /**
     * 总页数
     */
    @Schema(description = "总页数", example = "10")
    private Long pages;

    /**
     * 当前页码
     */
    @Schema(description = "当前页码", example = "1")
    private Integer pageNum;

    /**
     * 每页条数
     */
    @Schema(description = "每页条数", example = "10")
    private Integer pageSize;

    /**
     * 分页数据列表
     */
    @Schema(description = "分页数据列表")
    private List<T> records;

    /**
     * 封装MyBatis-Plus分页结果（修正基本类型long转Integer的问题）
     * @param <T> 数据类型
     * @return 通用分页VO
     */
    public static <T> PageVO<T> build(com.baomidou.mybatisplus.core.metadata.IPage<T> iPage) {
        PageVO<T> pageVO = new PageVO<>();
        pageVO.setTotal(iPage.getTotal());
        pageVO.setPages(iPage.getPages());

        // 修正点1：基本类型long 直接强转为 Integer（无需调用intValue()，包装类型才需要）
        pageVO.setPageNum((int) iPage.getCurrent());
        // 修正点2：同理，getSize()返回基本类型long，直接强转Integer
        pageVO.setPageSize((int) iPage.getSize());

        pageVO.setRecords(iPage.getRecords());
        return pageVO;
    }
}