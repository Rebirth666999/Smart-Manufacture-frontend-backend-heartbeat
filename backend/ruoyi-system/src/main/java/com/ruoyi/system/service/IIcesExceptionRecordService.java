package com.ruoyi.system.service;

import com.ruoyi.system.domain.vo.IcesExceptionRecordVo;
import com.ruoyi.system.domain.bo.IcesExceptionRecordBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 异常记录Service接口
 *
 * @author ruoyi
 * @date 2025-04-09
 */
public interface IIcesExceptionRecordService {

    /**
     * 查询异常记录
     */
    IcesExceptionRecordVo queryById(Long exrId);

    /**
     * 查询异常记录列表
     */
    TableDataInfo<IcesExceptionRecordVo> queryPageList(IcesExceptionRecordBo bo, PageQuery pageQuery);

    /**
     * 查询异常记录列表
     */
    List<IcesExceptionRecordVo> queryList(IcesExceptionRecordBo bo);

    /**
     * 新增异常记录
     */
    IcesExceptionRecordVo insertByBo(IcesExceptionRecordBo bo);

    /**
     * 修改异常记录
     */
    Boolean updateByBo(IcesExceptionRecordBo bo);

    String startLifecycle(IcesExceptionRecordBo bo);

    /**
     * 校验并批量删除异常记录信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
