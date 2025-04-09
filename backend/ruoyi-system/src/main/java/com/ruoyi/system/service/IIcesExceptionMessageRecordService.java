package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesExceptionMessageRecord;
import com.ruoyi.system.domain.vo.IcesExceptionMessageRecordVo;
import com.ruoyi.system.domain.bo.IcesExceptionMessageRecordBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 异常消息记录Service接口
 *
 * @author ruoyi
 * @date 2025-04-09
 */
public interface IIcesExceptionMessageRecordService {

    /**
     * 查询异常消息记录
     */
    IcesExceptionMessageRecordVo queryById(Long exmrId);

    /**
     * 查询异常消息记录列表
     */
    TableDataInfo<IcesExceptionMessageRecordVo> queryPageList(IcesExceptionMessageRecordBo bo, PageQuery pageQuery);

    /**
     * 查询异常消息记录列表
     */
    List<IcesExceptionMessageRecordVo> queryList(IcesExceptionMessageRecordBo bo);

    /**
     * 新增异常消息记录
     */
    Boolean insertByBo(IcesExceptionMessageRecordBo bo);

    /**
     * 修改异常消息记录
     */
    Boolean updateByBo(IcesExceptionMessageRecordBo bo);

    /**
     * 校验并批量删除异常消息记录信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
