package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesExceptionType;
import com.ruoyi.system.domain.vo.IcesExceptionTypeVo;
import com.ruoyi.system.domain.bo.IcesExceptionTypeBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 异常类型Service接口
 *
 * @author ruoyi
 * @date 2025-04-07
 */
public interface IIcesExceptionTypeService {

    /**
     * 查询异常类型
     */
    IcesExceptionTypeVo queryById(Long extId);

    /**
     * 查询异常类型列表
     */
    TableDataInfo<IcesExceptionTypeVo> queryPageList(IcesExceptionTypeBo bo, PageQuery pageQuery);

    /**
     * 查询异常类型列表
     */
    List<IcesExceptionTypeVo> queryList(IcesExceptionTypeBo bo);

    /**
     * 新增异常类型
     */
    Boolean insertByBo(IcesExceptionTypeBo bo);

    /**
     * 修改异常类型
     */
    Boolean updateByBo(IcesExceptionTypeBo bo);

    /**
     * 校验并批量删除异常类型信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
