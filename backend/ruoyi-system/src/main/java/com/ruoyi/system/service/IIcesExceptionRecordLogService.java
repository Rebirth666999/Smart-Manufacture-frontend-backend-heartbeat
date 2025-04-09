package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesExceptionRecordLog;
import com.ruoyi.system.domain.vo.IcesExceptionRecordLogVo;
import com.ruoyi.system.domain.bo.IcesExceptionRecordLogBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 异常处理日志Service接口
 *
 * @author ruoyi
 * @date 2025-04-09
 */
public interface IIcesExceptionRecordLogService {

    /**
     * 查询异常处理日志
     */
    IcesExceptionRecordLogVo queryById(Long exrlId);

    /**
     * 查询异常处理日志列表
     */
    TableDataInfo<IcesExceptionRecordLogVo> queryPageList(IcesExceptionRecordLogBo bo, PageQuery pageQuery);

    /**
     * 查询异常处理日志列表
     */
    List<IcesExceptionRecordLogVo> queryList(IcesExceptionRecordLogBo bo);

    /**
     * 新增异常处理日志
     */
    Boolean insertByBo(IcesExceptionRecordLogBo bo);

    /**
     * 修改异常处理日志
     */
    Boolean updateByBo(IcesExceptionRecordLogBo bo);

    /**
     * 校验并批量删除异常处理日志信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
