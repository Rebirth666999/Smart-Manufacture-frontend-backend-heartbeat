package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesExceptionParam;
import com.ruoyi.system.domain.vo.IcesExceptionParamVo;
import com.ruoyi.system.domain.bo.IcesExceptionParamBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 异常参数Service接口
 *
 * @author ruoyi
 * @date 2025-04-07
 */
public interface IIcesExceptionParamService {

    /**
     * 查询异常参数
     */
    IcesExceptionParamVo queryById(Long expId);

    /**
     * 查询异常参数列表
     */
    TableDataInfo<IcesExceptionParamVo> queryPageList(IcesExceptionParamBo bo, PageQuery pageQuery);

    /**
     * 查询异常参数列表
     */
    List<IcesExceptionParamVo> queryList(IcesExceptionParamBo bo);

    /**
     * 新增异常参数
     */
    Boolean insertByBo(IcesExceptionParamBo bo);

    /**
     * 修改异常参数
     */
    Boolean updateByBo(IcesExceptionParamBo bo);

    /**
     * 校验并批量删除异常参数信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
