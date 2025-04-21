package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesProductBatch;
import com.ruoyi.system.domain.vo.IcesProductBatchVo;
import com.ruoyi.system.domain.bo.IcesProductBatchBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 实际产品批次编码Service接口
 *
 * @author ruoyi
 * @date 2025-04-19
 */
public interface IIcesProductBatchService {

    /**
     * 查询实际产品批次编码
     */
    IcesProductBatchVo queryById(Long pbId);

    /**
     * 查询实际产品批次编码列表
     */
    TableDataInfo<IcesProductBatchVo> queryPageList(IcesProductBatchBo bo, PageQuery pageQuery);

    /**
     * 查询实际产品批次编码列表
     */
    List<IcesProductBatchVo> queryList(IcesProductBatchBo bo);

    /**
     * 新增实际产品批次编码
     */
    Boolean insertByBo(IcesProductBatchBo bo);

    /**
     * 修改实际产品批次编码
     */
    Boolean updateByBo(IcesProductBatchBo bo);

    /**
     * 校验并批量删除实际产品批次编码信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
