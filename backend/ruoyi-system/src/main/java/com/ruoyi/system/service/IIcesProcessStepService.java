package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesProcessStep;
import com.ruoyi.system.domain.vo.IcesProcessStepVo;
import com.ruoyi.system.domain.bo.IcesProcessStepBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 工艺步骤Service接口
 *
 * @author ruoyi
 * @date 2025-03-09
 */
public interface IIcesProcessStepService {

    /**
     * 查询工艺步骤
     */
    IcesProcessStepVo queryById(Long psId);

    /**
     * 查询工艺步骤列表
     */
    TableDataInfo<IcesProcessStepVo> queryPageList(IcesProcessStepBo bo, PageQuery pageQuery);

    /**
     * 查询工艺步骤列表
     */
    List<IcesProcessStepVo> queryList(IcesProcessStepBo bo);

    /**
     * 新增工艺步骤
     */
    Boolean insertByBo(IcesProcessStepBo bo);

    /**
     * 修改工艺步骤
     */
    Boolean updateByBo(IcesProcessStepBo bo);

    /**
     * 校验并批量删除工艺步骤信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
