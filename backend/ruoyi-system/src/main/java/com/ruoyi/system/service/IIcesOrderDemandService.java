package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesOrderDemand;
import com.ruoyi.system.domain.vo.IcesOrderDemandVo;
import com.ruoyi.system.domain.bo.IcesOrderDemandBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 订单所需产品关联Service接口
 *
 * @author ruoyi
 * @date 2025-04-10
 */
public interface IIcesOrderDemandService {

    /**
     * 查询订单所需产品关联
     */
    IcesOrderDemandVo queryById(Long odId);

    /**
     * 查询订单所需产品关联列表
     */
    TableDataInfo<IcesOrderDemandVo> queryPageList(IcesOrderDemandBo bo, PageQuery pageQuery);

    /**
     * 查询订单所需产品关联列表
     */
    List<IcesOrderDemandVo> queryList(IcesOrderDemandBo bo);

    /**
     * 新增订单所需产品关联
     */
    Boolean insertByBo(IcesOrderDemandBo bo);

    /**
     * 修改订单所需产品关联
     */
    Boolean updateByBo(IcesOrderDemandBo bo);

    /**
     * 校验并批量删除订单所需产品关联信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
