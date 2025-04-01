package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesOrder;
import com.ruoyi.system.domain.vo.IcesOrderVo;
import com.ruoyi.system.domain.bo.IcesOrderBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 订单Service接口
 *
 * @author ruoyi
 * @date 2025-03-17
 */
public interface IIcesOrderService {

    /**
     * 查询订单
     */
    IcesOrderVo queryById(Long orId);

    /**
     * 查询订单列表
     */
    TableDataInfo<IcesOrderVo> queryPageList(IcesOrderBo bo, PageQuery pageQuery);

    /**
     * 审核端查询状态
     */
    TableDataInfo<IcesOrderVo> queryReviewList(IcesOrderBo bo, PageQuery pageQuery);

    /**
     * 查询订单列表
     */
    List<IcesOrderVo> queryList(IcesOrderBo bo);

    /**
     * 新增订单
     */
    Boolean insertByBo(IcesOrderBo bo);

    /**
     * 修改订单
     */
    Boolean updateByBo(IcesOrderBo bo);

    /**
     * 校验并批量删除订单信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);


}
