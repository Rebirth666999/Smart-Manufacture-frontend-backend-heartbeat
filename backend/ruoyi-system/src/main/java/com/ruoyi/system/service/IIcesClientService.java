package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesClient;
import com.ruoyi.system.domain.vo.IcesClientVo;
import com.ruoyi.system.domain.bo.IcesClientBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.system.domain.vo.IcesManufacturePlanVo;

import java.util.Collection;
import java.util.List;

/**
 * 客户基本信息Service接口
 *
 * @author ruoyi
 * @date 2025-03-28
 */
public interface IIcesClientService {

    /**
     * 查询客户基本信息
     */
    IcesClientVo queryById(Long clId);

    /**
     * 查询客户基本信息列表
     */
    TableDataInfo<IcesClientVo> queryPageList(IcesClientBo bo, PageQuery pageQuery);

    /**
     * 查询客户基本信息列表
     */
    List<IcesClientVo> queryList(IcesClientBo bo);

    /**
     * 新增客户基本信息
     */
    IcesClientVo insertByBo(IcesClientBo bo);

    /**
     * 修改客户基本信息
     */
    Boolean updateByBo(IcesClientBo bo);

    /**
     * 校验并批量删除客户基本信息信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
