package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesProductInspection;
import com.ruoyi.system.domain.vo.IcesProductInspectionVo;
import com.ruoyi.system.domain.bo.IcesProductInspectionBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 产品质检单Service接口
 *
 * @author ruoyi
 * @date 2025-04-19
 */
public interface IIcesProductInspectionService {

    /**
     * 查询产品质检单
     */
    IcesProductInspectionVo queryById(Long piId);

    /**
     * 查询产品质检单列表
     */
    TableDataInfo<IcesProductInspectionVo> queryPageList(IcesProductInspectionBo bo, PageQuery pageQuery);

    /**
     * 查询产品质检单列表
     */
    List<IcesProductInspectionVo> queryList(IcesProductInspectionBo bo);

    /**
     * 新增产品质检单
     */
    Boolean insertByBo(IcesProductInspectionBo bo);

    /**
     * 修改产品质检单
     */
    Boolean updateByBo(IcesProductInspectionBo bo);

    /**
     * 校验并批量删除产品质检单信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
