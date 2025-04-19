package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesProductLedger;
import com.ruoyi.system.domain.vo.IcesProductLedgerVo;
import com.ruoyi.system.domain.bo.IcesProductLedgerBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 产品台账Service接口
 *
 * @author ruoyi
 * @date 2025-04-18
 */
public interface IIcesProductLedgerService {

    /**
     * 查询产品台账
     */
    IcesProductLedgerVo queryById(Long plId);

    /**
     * 查询产品台账列表
     */
    TableDataInfo<IcesProductLedgerVo> queryPageList(IcesProductLedgerBo bo, PageQuery pageQuery);

    /**
     * 查询产品台账列表
     */
    List<IcesProductLedgerVo> queryList(IcesProductLedgerBo bo);

    /**
     * 新增产品台账
     */
    Boolean insertByBo(IcesProductLedgerBo bo);

    /**
     * 修改产品台账
     */
    Boolean updateByBo(IcesProductLedgerBo bo);

    /**
     * 校验并批量删除产品台账信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
