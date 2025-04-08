package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesExceptionParamMap;
import com.ruoyi.system.domain.vo.IcesExceptionParamMapVo;
import com.ruoyi.system.domain.bo.IcesExceptionParamMapBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 异常参数模板条目Service接口
 *
 * @author ruoyi
 * @date 2025-04-08
 */
public interface IIcesExceptionParamMapService {

    /**
     * 查询异常参数模板条目
     */
    IcesExceptionParamMapVo queryById(Long expmId);

    /**
     * 查询异常参数模板条目列表
     */
    TableDataInfo<IcesExceptionParamMapVo> queryPageList(IcesExceptionParamMapBo bo, PageQuery pageQuery);

    /**
     * 查询异常参数模板条目列表
     */
    List<IcesExceptionParamMapVo> queryList(IcesExceptionParamMapBo bo);

    /**
     * 新增异常参数模板条目
     */
    Boolean insertByBo(IcesExceptionParamMapBo bo);

    /**
     * 修改异常参数模板条目
     */
    Boolean updateByBo(IcesExceptionParamMapBo bo);

    /**
     * 校验并批量删除异常参数模板条目信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
