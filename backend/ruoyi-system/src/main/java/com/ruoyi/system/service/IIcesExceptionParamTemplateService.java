package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesExceptionParamTemplate;
import com.ruoyi.system.domain.vo.IcesExceptionParamTemplateVo;
import com.ruoyi.system.domain.bo.IcesExceptionParamTemplateBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 异常参数模板Service接口
 *
 * @author ruoyi
 * @date 2025-04-07
 */
public interface IIcesExceptionParamTemplateService {

    /**
     * 查询异常参数模板
     */
    IcesExceptionParamTemplateVo queryById(Long exptId);

    /**
     * 查询异常参数模板列表
     */
    TableDataInfo<IcesExceptionParamTemplateVo> queryPageList(IcesExceptionParamTemplateBo bo, PageQuery pageQuery);

    /**
     * 查询异常参数模板列表
     */
    List<IcesExceptionParamTemplateVo> queryList(IcesExceptionParamTemplateBo bo);

    /**
     * 新增异常参数模板
     */
    Boolean insertByBo(IcesExceptionParamTemplateBo bo);

    /**
     * 修改异常参数模板
     */
    Boolean updateByBo(IcesExceptionParamTemplateBo bo);

    /**
     * 校验并批量删除异常参数模板信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
