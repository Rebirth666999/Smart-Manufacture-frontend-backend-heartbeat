package com.ruoyi.system.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ruoyi.system.domain.vo.IcesExceptionLifecycleVo;
import com.ruoyi.system.domain.bo.IcesExceptionLifecycleBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import org.dom4j.DocumentException;

import java.util.Collection;
import java.util.List;

/**
 * 异常生命周期Service接口
 *
 * @author ruoyi
 * @date 2025-04-08
 */
public interface IIcesExceptionLifecycleService {

    /**
     * 查询异常生命周期
     */
    IcesExceptionLifecycleVo queryById(Long exlId);

    /**
     * 查询异常生命周期列表
     */
    TableDataInfo<IcesExceptionLifecycleVo> queryPageList(IcesExceptionLifecycleBo bo, PageQuery pageQuery);

    /**
     * 查询异常生命周期列表
     */
    List<IcesExceptionLifecycleVo> queryList(IcesExceptionLifecycleBo bo);

    /**
     * 新增异常生命周期
     */
    Boolean insertByBo(IcesExceptionLifecycleBo bo);

    /**
     * 修改异常生命周期
     */
    Boolean updateByBo(IcesExceptionLifecycleBo bo);

    /**
     * 校验并批量删除异常生命周期信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    Boolean saveModel(String json) throws JsonProcessingException, DocumentException;

    String getModel(String exlId);

    String queryBpmnXmlById(String modelId);

    Boolean deployModel(IcesExceptionLifecycleBo bo);
}
