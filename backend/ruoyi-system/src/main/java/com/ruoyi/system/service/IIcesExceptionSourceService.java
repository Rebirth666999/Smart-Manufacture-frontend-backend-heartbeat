package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesExceptionSource;
import com.ruoyi.system.domain.vo.IcesExceptionSourceVo;
import com.ruoyi.system.domain.bo.IcesExceptionSourceBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 异常源Service接口
 *
 * @author ruoyi
 * @date 2025-04-07
 */
public interface IIcesExceptionSourceService {

    /**
     * 查询异常源
     */
    IcesExceptionSourceVo queryById(Long exsId);

    /**
     * 查询异常源列表
     */
    TableDataInfo<IcesExceptionSourceVo> queryPageList(IcesExceptionSourceBo bo, PageQuery pageQuery);

    /**
     * 查询异常源列表
     */
    List<IcesExceptionSourceVo> queryList(IcesExceptionSourceBo bo);

    /**
     * 新增异常源
     */
    Boolean insertByBo(IcesExceptionSourceBo bo);

    /**
     * 修改异常源
     */
    Boolean updateByBo(IcesExceptionSourceBo bo);

    /**
     * 校验并批量删除异常源信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
