package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesMaterialRecord;
import com.ruoyi.system.domain.vo.IcesMaterialRecordVo;
import com.ruoyi.system.domain.bo.IcesMaterialRecordBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 原料台账Service接口
 *
 * @author ruoyi
 * @date 2025-02-14
 */
public interface IIcesMaterialRecordService {

    /**
     * 查询原料台账
     */
    IcesMaterialRecordVo queryById(Long mrId);

    /**
     * 查询原料台账列表
     */
    TableDataInfo<IcesMaterialRecordVo> queryPageList(IcesMaterialRecordBo bo, PageQuery pageQuery);

    /**
     * 查询原料台账列表
     */
    List<IcesMaterialRecordVo> queryList(IcesMaterialRecordBo bo);

    /**
     * 新增原料台账
     */
    Boolean insertByBo(IcesMaterialRecordBo bo);

    /**
     * 修改原料台账
     */
    Boolean updateByBo(IcesMaterialRecordBo bo);

    /**
     * 校验并批量删除原料台账信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
