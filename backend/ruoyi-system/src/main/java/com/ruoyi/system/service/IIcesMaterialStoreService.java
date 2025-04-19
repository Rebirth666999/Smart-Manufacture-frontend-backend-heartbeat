package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesMaterialStore;
import com.ruoyi.system.domain.vo.IcesMaterialStoreVo;
import com.ruoyi.system.domain.bo.IcesMaterialStoreBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 原料仓库Service接口
 *
 * @author ruoyi
 * @date 2025-04-17
 */
public interface IIcesMaterialStoreService {

    /**
     * 查询原料仓库
     */
    IcesMaterialStoreVo queryById(Long msId);

    /**
     * 查询原料仓库列表
     */
    TableDataInfo<IcesMaterialStoreVo> queryPageList(IcesMaterialStoreBo bo, PageQuery pageQuery);

    /**
     * 查询原料仓库列表
     */
    List<IcesMaterialStoreVo> queryList(IcesMaterialStoreBo bo);

    /**
     * 新增原料仓库
     */
    Boolean insertByBo(IcesMaterialStoreBo bo);

    /**
     * 修改原料仓库
     */
    Boolean updateByBo(IcesMaterialStoreBo bo);

    /**
     * 校验并批量删除原料仓库信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
