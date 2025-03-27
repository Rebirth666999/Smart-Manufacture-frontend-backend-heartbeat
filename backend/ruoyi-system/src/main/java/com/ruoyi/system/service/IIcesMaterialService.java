package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesMaterial;
import com.ruoyi.system.domain.vo.IcesMaterialVo;
import com.ruoyi.system.domain.bo.IcesMaterialBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 物料Service接口
 *
 * @author ruoyi
 * @date 2025-03-26
 */
public interface IIcesMaterialService {

    /**
     * 查询物料
     */
    IcesMaterialVo queryById(Long maId);

    /**
     * 查询物料列表
     */
    TableDataInfo<IcesMaterialVo> queryPageList(IcesMaterialBo bo, PageQuery pageQuery);

    /**
     * 查询物料列表
     */
    List<IcesMaterialVo> queryList(IcesMaterialBo bo);

    /**
     * 新增物料
     */
    Boolean insertByBo(IcesMaterialBo bo);

    /**
     * 修改物料
     */
    Boolean updateByBo(IcesMaterialBo bo);

    /**
     * 校验并批量删除物料信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
