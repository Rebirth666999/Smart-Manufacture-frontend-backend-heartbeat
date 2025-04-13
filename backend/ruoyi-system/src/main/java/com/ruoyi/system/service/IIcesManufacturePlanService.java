package com.ruoyi.system.service;

import com.ruoyi.system.domain.bo.IcesManufactureTaskBo;
import com.ruoyi.system.domain.vo.IcesManufacturePlanVo;
import com.ruoyi.system.domain.bo.IcesManufacturePlanBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 生产计划Service接口
 *
 * @author ruoyi
 * @date 2025-03-17
 */
public interface IIcesManufacturePlanService {

    /**
     * 查询生产计划
     */
    IcesManufacturePlanVo queryById(Long mpId);

    /**
     * 查询生产计划列表
     */
    TableDataInfo<IcesManufacturePlanVo> queryPageList(IcesManufacturePlanBo bo, PageQuery pageQuery);

    /**
     * 查询审核状态
     */
    TableDataInfo<IcesManufacturePlanVo> queryReviewList(IcesManufacturePlanBo bo, PageQuery pageQuery);

    /**
     * 查询生产计划列表
     */
    List<IcesManufacturePlanVo> queryList(IcesManufacturePlanBo bo);

    /**
     * 新增生产计划
     */
    IcesManufacturePlanVo insertByBo(IcesManufacturePlanBo bo);

    /**
     * 修改生产计划
     */
    Boolean updateByBo(IcesManufacturePlanBo bo);

    /**
     * 校验并批量删除生产计划信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     *更新状态
     * @param icesManufactureTaskBo
     */
    void updateStatus(IcesManufactureTaskBo icesManufactureTaskBo);
}
