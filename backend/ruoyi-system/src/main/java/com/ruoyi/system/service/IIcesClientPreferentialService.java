package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesClientPreferential;
import com.ruoyi.system.domain.vo.IcesClientPreferentialVo;
import com.ruoyi.system.domain.bo.IcesClientPreferentialBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 客户优惠策略Service接口
 *
 * @author ruoyi
 * @date 2025-03-24
 */
public interface IIcesClientPreferentialService {

    /**
     * 查询客户优惠策略
     */
    IcesClientPreferentialVo queryById(Long cpId);

    /**
     * 查询客户优惠策略列表
     */
    TableDataInfo<IcesClientPreferentialVo> queryPageList(IcesClientPreferentialBo bo, PageQuery pageQuery);

    /**
     * 查询客户优惠策略列表
     */
    List<IcesClientPreferentialVo> queryList(IcesClientPreferentialBo bo);

    /**
     * 新增客户优惠策略
     */
    Boolean insertByBo(IcesClientPreferentialBo bo);

    /**
     * 修改客户优惠策略
     */
    Boolean updateByBo(IcesClientPreferentialBo bo);

    /**
     * 校验并批量删除客户优惠策略信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
