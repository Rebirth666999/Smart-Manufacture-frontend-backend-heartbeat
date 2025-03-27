package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesProcessStepPrevRound;
import com.ruoyi.system.domain.vo.IcesProcessStepPrevRoundVo;
import com.ruoyi.system.domain.bo.IcesProcessStepPrevRoundBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 关联-工艺步骤的跨轮次前序步骤Service接口
 *
 * @author ruoyi
 * @date 2025-03-09
 */
public interface IIcesProcessStepPrevRoundService {

    /**
     * 查询关联-工艺步骤的跨轮次前序步骤
     */
    IcesProcessStepPrevRoundVo queryById(Long psprId);

    IcesProcessStepPrevRoundVo queryByCurId(Long psIdCur);

    /**
     * 查询关联-工艺步骤的跨轮次前序步骤列表
     */
    TableDataInfo<IcesProcessStepPrevRoundVo> queryPageList(IcesProcessStepPrevRoundBo bo, PageQuery pageQuery);

    /**
     * 查询关联-工艺步骤的跨轮次前序步骤列表
     */
    List<IcesProcessStepPrevRoundVo> queryList(IcesProcessStepPrevRoundBo bo);

    /**
     * 新增关联-工艺步骤的跨轮次前序步骤
     */
    Boolean insertByBo(IcesProcessStepPrevRoundBo bo);

    /**
     * 修改关联-工艺步骤的跨轮次前序步骤
     */
    Boolean updateByBo(IcesProcessStepPrevRoundBo bo);

    /**
     * 校验并批量删除关联-工艺步骤的跨轮次前序步骤信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);


}
