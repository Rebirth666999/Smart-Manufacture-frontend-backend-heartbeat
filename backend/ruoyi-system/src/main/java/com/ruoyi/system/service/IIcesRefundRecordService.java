package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesRefundRecord;
import com.ruoyi.system.domain.vo.IcesRefundRecordVo;
import com.ruoyi.system.domain.bo.IcesRefundRecordBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 退货记录Service接口
 *
 * @author ruoyi
 * @date 2025-03-28
 */
public interface IIcesRefundRecordService {

    /**
     * 查询退货记录
     */
    IcesRefundRecordVo queryById(Long rrId);

    /**
     * 查询退货记录列表
     */
    TableDataInfo<IcesRefundRecordVo> queryPageList(IcesRefundRecordBo bo, PageQuery pageQuery);

    /**
     * 查询退货记录列表
     */
    List<IcesRefundRecordVo> queryList(IcesRefundRecordBo bo);

    /**
     * 新增退货记录
     */
    Boolean insertByBo(IcesRefundRecordBo bo);

    /**
     * 修改退货记录
     */
    Boolean updateByBo(IcesRefundRecordBo bo);

    /**
     * 校验并批量删除退货记录信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
