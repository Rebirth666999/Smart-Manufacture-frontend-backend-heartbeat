package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesProcessStepPrev;
import com.ruoyi.system.domain.vo.IcesProcessStepPrevVo;
import com.ruoyi.system.domain.bo.IcesProcessStepPrevBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 关联-工艺步骤的前序步骤Service接口
 *
 * @author ruoyi
 * @date 2025-03-09
 */
public interface IIcesProcessStepPrevService {

    /**
     * 查询关联-工艺步骤的前序步骤
     */
    IcesProcessStepPrevVo queryById(Long pspId);

    IcesProcessStepPrevVo queryByCurId(Long pspIdCur);

    /**
     * 查询关联-工艺步骤的前序步骤列表
     */
    TableDataInfo<IcesProcessStepPrevVo> queryPageList(IcesProcessStepPrevBo bo, PageQuery pageQuery);

    /**
     * 查询关联-工艺步骤的前序步骤列表
     */
    List<IcesProcessStepPrevVo> queryList(IcesProcessStepPrevBo bo);

    /**
     * 新增关联-工艺步骤的前序步骤
     */
    Boolean insertByBo(IcesProcessStepPrevBo bo);

    /**
     * 修改关联-工艺步骤的前序步骤
     */
    Boolean updateByBo(IcesProcessStepPrevBo bo);

    /**
     * 校验并批量删除关联-工艺步骤的前序步骤信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);


}
