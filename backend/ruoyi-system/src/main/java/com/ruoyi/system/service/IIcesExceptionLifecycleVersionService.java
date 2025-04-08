package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesExceptionLifecycleVersion;
import com.ruoyi.system.domain.vo.IcesExceptionLifecycleVersionVo;
import com.ruoyi.system.domain.bo.IcesExceptionLifecycleVersionBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 异常生命周期版本Service接口
 *
 * @author ruoyi
 * @date 2025-04-08
 */
public interface IIcesExceptionLifecycleVersionService {

    /**
     * 查询异常生命周期版本
     */
    IcesExceptionLifecycleVersionVo queryById(Long exlvId);

    /**
     * 查询异常生命周期版本列表
     */
    TableDataInfo<IcesExceptionLifecycleVersionVo> queryPageList(IcesExceptionLifecycleVersionBo bo, PageQuery pageQuery);

    /**
     * 查询异常生命周期版本列表
     */
    List<IcesExceptionLifecycleVersionVo> queryList(IcesExceptionLifecycleVersionBo bo);

    /**
     * 新增异常生命周期版本
     */
    Boolean insertByBo(IcesExceptionLifecycleVersionBo bo);

    /**
     * 修改异常生命周期版本
     */
    Boolean updateByBo(IcesExceptionLifecycleVersionBo bo);

    /**
     * 校验并批量删除异常生命周期版本信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
