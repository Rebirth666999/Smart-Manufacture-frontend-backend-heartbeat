package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesProductRecord;
import com.ruoyi.system.domain.vo.IcesProductRecordVo;
import com.ruoyi.system.domain.bo.IcesProductRecordBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 产品台账Service接口
 *
 * @author ruoyi
 * @date 2025-02-18
 */
public interface IIcesProductRecordService {

    /**
     * 查询产品台账
     */
    IcesProductRecordVo queryById(Long prrId);

    /**
     * 查询产品台账列表
     */
    TableDataInfo<IcesProductRecordVo> queryPageList(IcesProductRecordBo bo, PageQuery pageQuery);

    /**
     * 查询产品台账列表
     */
    List<IcesProductRecordVo> queryList(IcesProductRecordBo bo);

    /**
     * 新增产品台账
     */
    Boolean insertByBo(IcesProductRecordBo bo);

    /**
     * 修改产品台账
     */
    Boolean updateByBo(IcesProductRecordBo bo);

    /**
     * 校验并批量删除产品台账信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
