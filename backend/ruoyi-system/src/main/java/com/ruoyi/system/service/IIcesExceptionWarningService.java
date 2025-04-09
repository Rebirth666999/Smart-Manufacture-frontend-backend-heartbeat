package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesExceptionWarning;
import com.ruoyi.system.domain.vo.IcesExceptionWarningVo;
import com.ruoyi.system.domain.bo.IcesExceptionWarningBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 异常预警配置Service接口
 *
 * @author ruoyi
 * @date 2025-04-09
 */
public interface IIcesExceptionWarningService {

    /**
     * 查询异常预警配置
     */
    IcesExceptionWarningVo queryById(Long exwId);

    /**
     * 查询异常预警配置列表
     */
    TableDataInfo<IcesExceptionWarningVo> queryPageList(IcesExceptionWarningBo bo, PageQuery pageQuery);

    /**
     * 查询异常预警配置列表
     */
    List<IcesExceptionWarningVo> queryList(IcesExceptionWarningBo bo);

    /**
     * 新增异常预警配置
     */
    Boolean insertByBo(IcesExceptionWarningBo bo);

    /**
     * 修改异常预警配置
     */
    Boolean updateByBo(IcesExceptionWarningBo bo);

    /**
     * 校验并批量删除异常预警配置信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
