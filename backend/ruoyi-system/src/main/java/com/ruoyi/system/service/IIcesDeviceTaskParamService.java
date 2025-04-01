package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesDeviceTaskParam;
import com.ruoyi.system.domain.vo.IcesDeviceTaskParamVo;
import com.ruoyi.system.domain.bo.IcesDeviceTaskParamBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 设备任务参数Service接口
 *
 * @author ruoyi
 * @date 2025-03-24
 */
public interface IIcesDeviceTaskParamService {

    /**
     * 查询设备任务参数
     */
    IcesDeviceTaskParamVo queryById(Long dtpaId);

    /**
     * 查询设备任务参数列表
     */
    TableDataInfo<IcesDeviceTaskParamVo> queryPageList(IcesDeviceTaskParamBo bo, PageQuery pageQuery);

    /**
     * 查询设备任务参数列表
     */
    List<IcesDeviceTaskParamVo> queryList(IcesDeviceTaskParamBo bo);

    /**
     * 新增设备任务参数
     */
    Boolean insertByBo(IcesDeviceTaskParamBo bo);

    /**
     * 修改设备任务参数
     */
    Boolean updateByBo(IcesDeviceTaskParamBo bo);

    /**
     * 校验并批量删除设备任务参数信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
