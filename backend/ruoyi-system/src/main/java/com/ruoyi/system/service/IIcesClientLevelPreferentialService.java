package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesClientLevelPreferential;
import com.ruoyi.system.domain.vo.IcesClientLevelPreferentialVo;
import com.ruoyi.system.domain.bo.IcesClientLevelPreferentialBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 关联-客户等级对应的优惠策略Service接口
 *
 * @author ruoyi
 * @date 2025-03-27
 */
public interface IIcesClientLevelPreferentialService {

    /**
     * 查询关联-客户等级对应的优惠策略
     */
    IcesClientLevelPreferentialVo queryById(Long clpId);

    /**
     * 查询关联-客户等级对应的优惠策略列表
     */
    TableDataInfo<IcesClientLevelPreferentialVo> queryPageList(IcesClientLevelPreferentialBo bo, PageQuery pageQuery);

    /**
     * 查询关联-客户等级对应的优惠策略列表
     */
    List<IcesClientLevelPreferentialVo> queryList(IcesClientLevelPreferentialBo bo);

    /**
     * 新增关联-客户等级对应的优惠策略
     */
    Boolean insertByBo(IcesClientLevelPreferentialBo bo);

    /**
     * 修改关联-客户等级对应的优惠策略
     */
    Boolean updateByBo(IcesClientLevelPreferentialBo bo);

    /**
     * 校验并批量删除关联-客户等级对应的优惠策略信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
