package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesMaterialLedger;
import com.ruoyi.system.domain.vo.IcesMaterialLedgerVo;
import com.ruoyi.system.domain.bo.IcesMaterialLedgerBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 原料台账Service接口
 *
 * @author ruoyi
 * @date 2025-04-18
 */
public interface IIcesMaterialLedgerService {

    /**
     * 查询原料台账
     */
    IcesMaterialLedgerVo queryById(Long mlId);

    /**
     * 查询原料台账列表
     */
    TableDataInfo<IcesMaterialLedgerVo> queryPageList(IcesMaterialLedgerBo bo, PageQuery pageQuery);

    /**
     * 查询原料台账列表
     */
    List<IcesMaterialLedgerVo> queryList(IcesMaterialLedgerBo bo);

    /**
     * 新增原料台账
     */
    Boolean insertByBo(IcesMaterialLedgerBo bo);

    /**
     * 修改原料台账
     */
    Boolean updateByBo(IcesMaterialLedgerBo bo);

    /**
     * 校验并批量删除原料台账信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
