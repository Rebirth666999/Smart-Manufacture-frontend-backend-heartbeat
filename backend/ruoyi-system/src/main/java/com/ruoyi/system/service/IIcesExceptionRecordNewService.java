package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesExceptionRecordNew;
import com.ruoyi.system.domain.vo.IcesExceptionRecordNewVo;
import com.ruoyi.system.domain.bo.IcesExceptionRecordNewBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 异常记录（新）Service接口
 *
 * @author ruoyi
 * @date 2025-07-30
 */
public interface IIcesExceptionRecordNewService {

    /**
     * 查询异常记录（新）
     */
    IcesExceptionRecordNewVo queryById(Long exrId);

    /**
     * 查询异常记录（新）列表
     */
    TableDataInfo<IcesExceptionRecordNewVo> queryPageList(IcesExceptionRecordNewBo bo, PageQuery pageQuery);

    /**
     * 查询异常记录（新）列表
     */
    List<IcesExceptionRecordNewVo> queryList(IcesExceptionRecordNewBo bo);

    /**
     * 新增异常记录（新）
     */
    Boolean insertByBo(IcesExceptionRecordNewBo bo);

    /**
     * 修改异常记录（新）
     */
    Boolean updateByBo(IcesExceptionRecordNewBo bo);

    /**
     * 校验并批量删除异常记录（新）信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
