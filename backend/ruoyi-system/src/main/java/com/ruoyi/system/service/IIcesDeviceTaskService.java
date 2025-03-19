package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesDeviceTask;
import com.ruoyi.system.domain.vo.IcesDeviceTaskVo;
import com.ruoyi.system.domain.bo.IcesDeviceTaskBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 设备任务Service接口
 *
 * @author ruoyi
 * @date 2025-03-19
 */
public interface IIcesDeviceTaskService {

    /**
     * 查询设备任务
     */
    IcesDeviceTaskVo queryById(Long dtId);

    /**
     * 查询设备任务列表
     */
    TableDataInfo<IcesDeviceTaskVo> queryPageList(IcesDeviceTaskBo bo, PageQuery pageQuery);

    /**
     * 查询设备任务列表
     */
    List<IcesDeviceTaskVo> queryList(IcesDeviceTaskBo bo);

    /**
     * 新增设备任务
     */
    Boolean insertByBo(IcesDeviceTaskBo bo);

    /**
     * 修改设备任务
     */
    Boolean updateByBo(IcesDeviceTaskBo bo);

    /**
     * 校验并批量删除设备任务信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
