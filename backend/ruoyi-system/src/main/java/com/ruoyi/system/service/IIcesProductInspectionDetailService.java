package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesProductInspectionDetail;
import com.ruoyi.system.domain.vo.IcesProductInspectionDetailVo;
import com.ruoyi.system.domain.bo.IcesProductInspectionDetailBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 产品质检单明细Service接口
 *
 * @author ruoyi
 * @date 2025-04-19
 */
public interface IIcesProductInspectionDetailService {

    /**
     * 查询产品质检单明细
     */
    IcesProductInspectionDetailVo queryById(Long pidId);

    /**
     * 查询产品质检单明细列表
     */
    TableDataInfo<IcesProductInspectionDetailVo> queryPageList(IcesProductInspectionDetailBo bo, PageQuery pageQuery);

    /**
     * 查询产品质检单明细列表
     */
    List<IcesProductInspectionDetailVo> queryList(IcesProductInspectionDetailBo bo);

    /**
     * 新增产品质检单明细
     */
    Boolean insertByBo(IcesProductInspectionDetailBo bo);

    /**
     * 修改产品质检单明细
     */
    Boolean updateByBo(IcesProductInspectionDetailBo bo);

    /**
     * 校验并批量删除产品质检单明细信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
