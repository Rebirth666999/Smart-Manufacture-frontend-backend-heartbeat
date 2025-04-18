package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesProductStock;
import com.ruoyi.system.domain.vo.IcesProductStockVo;
import com.ruoyi.system.domain.bo.IcesProductStockBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 仓库产品库存Service接口
 *
 * @author ruoyi
 * @date 2025-04-18
 */
public interface IIcesProductStockService {

    /**
     * 查询仓库产品库存
     */
    IcesProductStockVo queryById(Long pssId);

    /**
     * 查询仓库产品库存列表
     */
    TableDataInfo<IcesProductStockVo> queryPageList(IcesProductStockBo bo, PageQuery pageQuery);

    /**
     * 查询仓库产品库存列表
     */
    List<IcesProductStockVo> queryList(IcesProductStockBo bo);

    /**
     * 新增仓库产品库存
     */
    Boolean insertByBo(IcesProductStockBo bo);

    /**
     * 修改仓库产品库存
     */
    Boolean updateByBo(IcesProductStockBo bo);

    /**
     * 校验并批量删除仓库产品库存信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
