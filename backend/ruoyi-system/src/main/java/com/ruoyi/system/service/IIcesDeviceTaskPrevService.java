package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesDeviceTaskPrev;
import com.ruoyi.system.domain.vo.IcesDeviceTaskPrevVo;
import com.ruoyi.system.domain.bo.IcesDeviceTaskPrevBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 关联-设备任务的前序任务Service接口
 *
 * @author ruoyi
 * @date 2025-03-24
 */
public interface IIcesDeviceTaskPrevService {

    /**
     * 查询关联-设备任务的前序任务
     */
    IcesDeviceTaskPrevVo queryById(Long dtprId);

    /**
     * 查询关联-设备任务的前序任务列表
     */
    TableDataInfo<IcesDeviceTaskPrevVo> queryPageList(IcesDeviceTaskPrevBo bo, PageQuery pageQuery);

    /**
     * 查询关联-设备任务的前序任务列表
     */
    List<IcesDeviceTaskPrevVo> queryList(IcesDeviceTaskPrevBo bo);

    /**
     * 新增关联-设备任务的前序任务
     */
    Boolean insertByBo(IcesDeviceTaskPrevBo bo);

    /**
     * 修改关联-设备任务的前序任务
     */
    Boolean updateByBo(IcesDeviceTaskPrevBo bo);

    /**
     * 校验并批量删除关联-设备任务的前序任务信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
