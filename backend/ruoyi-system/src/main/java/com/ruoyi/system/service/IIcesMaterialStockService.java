package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesMaterialStock;
import com.ruoyi.system.domain.vo.IcesMaterialStockVo;
import com.ruoyi.system.domain.bo.IcesMaterialStockBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 仓库原料库存Service接口
 *
 * @author ruoyi
 * @date 2025-04-18
 */
public interface IIcesMaterialStockService {

    /**
     * 查询仓库原料库存
     */
    IcesMaterialStockVo queryById(Long mssId);

    /**
     * 查询仓库原料库存列表
     */
    TableDataInfo<IcesMaterialStockVo> queryPageList(IcesMaterialStockBo bo, PageQuery pageQuery);

    /**
     * 查询仓库原料库存列表
     */
    List<IcesMaterialStockVo> queryList(IcesMaterialStockBo bo);

    /**
     * 新增仓库原料库存
     */
    Boolean insertByBo(IcesMaterialStockBo bo);

    /**
     * 修改仓库原料库存
     */
    Boolean updateByBo(IcesMaterialStockBo bo);

    /**
     * 校验并批量删除仓库原料库存信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
