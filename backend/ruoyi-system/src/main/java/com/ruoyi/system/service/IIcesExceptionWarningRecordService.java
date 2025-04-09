package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesExceptionWarningRecord;
import com.ruoyi.system.domain.vo.IcesExceptionWarningRecordVo;
import com.ruoyi.system.domain.bo.IcesExceptionWarningRecordBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 异常预警记录Service接口
 *
 * @author ruoyi
 * @date 2025-04-09
 */
public interface IIcesExceptionWarningRecordService {

    /**
     * 查询异常预警记录
     */
    IcesExceptionWarningRecordVo queryById(Long exwrId);

    /**
     * 查询异常预警记录列表
     */
    TableDataInfo<IcesExceptionWarningRecordVo> queryPageList(IcesExceptionWarningRecordBo bo, PageQuery pageQuery);

    /**
     * 查询异常预警记录列表
     */
    List<IcesExceptionWarningRecordVo> queryList(IcesExceptionWarningRecordBo bo);

    /**
     * 新增异常预警记录
     */
    Boolean insertByBo(IcesExceptionWarningRecordBo bo);

    /**
     * 修改异常预警记录
     */
    Boolean updateByBo(IcesExceptionWarningRecordBo bo);

    /**
     * 校验并批量删除异常预警记录信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
