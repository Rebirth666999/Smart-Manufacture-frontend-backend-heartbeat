package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesClientTrade;
import com.ruoyi.system.domain.vo.IcesClientTradeVo;
import com.ruoyi.system.domain.bo.IcesClientTradeBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 客户贸易信息Service接口
 *
 * @author ruoyi
 * @date 2025-03-28
 */
public interface IIcesClientTradeService {

    /**
     * 查询客户贸易信息
     */
    IcesClientTradeVo queryById(Long ctId);

    /**
     * 查询客户贸易信息列表
     */
    TableDataInfo<IcesClientTradeVo> queryPageList(IcesClientTradeBo bo, PageQuery pageQuery);

    /**
     * 查询客户贸易信息列表
     */
    List<IcesClientTradeVo> queryList(IcesClientTradeBo bo);

    /**
     * 新增客户贸易信息
     */
    Boolean insertByBo(IcesClientTradeBo bo);

    /**
     * 修改客户贸易信息
     */
    Boolean updateByBo(IcesClientTradeBo bo);

    /**
     * 校验并批量删除客户贸易信息信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
