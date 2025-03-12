package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesProcessMaterial;
import com.ruoyi.system.domain.vo.IcesProcessMaterialVo;
import com.ruoyi.system.domain.bo.IcesProcessMaterialBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 关联-工艺流程原料需求Service接口
 *
 * @author ruoyi
 * @date 2025-03-12
 */
public interface IIcesProcessMaterialService {

    /**
     * 查询关联-工艺流程原料需求
     */
    IcesProcessMaterialVo queryById(Long pmId);

    /**
     * 查询关联-工艺流程原料需求列表
     */
    TableDataInfo<IcesProcessMaterialVo> queryPageList(IcesProcessMaterialBo bo, PageQuery pageQuery);

    /**
     * 查询关联-工艺流程原料需求列表
     */
    List<IcesProcessMaterialVo> queryList(IcesProcessMaterialBo bo);

    /**
     * 新增关联-工艺流程原料需求
     */
    Boolean insertByBo(IcesProcessMaterialBo bo);

    /**
     * 修改关联-工艺流程原料需求
     */
    Boolean updateByBo(IcesProcessMaterialBo bo);

    /**
     * 校验并批量删除关联-工艺流程原料需求信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
