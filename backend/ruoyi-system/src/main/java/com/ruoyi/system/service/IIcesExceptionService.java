package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesException;
import com.ruoyi.system.domain.vo.IcesExceptionVo;
import com.ruoyi.system.domain.bo.IcesExceptionBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 异常Service接口
 *
 * @author ruoyi
 * @date 2025-04-07
 */
public interface IIcesExceptionService {

    /**
     * 查询异常
     */
    IcesExceptionVo queryById(Long exId);

    /**
     * 查询异常列表
     */
    TableDataInfo<IcesExceptionVo> queryPageList(IcesExceptionBo bo, PageQuery pageQuery);

    /**
     * 查询异常列表
     */
    List<IcesExceptionVo> queryList(IcesExceptionBo bo);

    /**
     * 新增异常
     */
    Boolean insertByBo(IcesExceptionBo bo);

    /**
     * 修改异常
     */
    Boolean updateByBo(IcesExceptionBo bo);

    /**
     * 校验并批量删除异常信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
