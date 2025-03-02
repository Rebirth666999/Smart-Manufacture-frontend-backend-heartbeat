package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesEquipmentRecord;
import com.ruoyi.system.domain.vo.IcesEquipmentRecordVo;
import com.ruoyi.system.domain.bo.IcesEquipmentRecordBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 设备事件日志Service接口
 *
 * @author ruoyi
 * @date 2025-03-01
 */
public interface IIcesEquipmentRecordService {

    /**
     * 查询设备事件日志
     */
    IcesEquipmentRecordVo queryById(Long erId);

    /**
     * 查询设备事件日志列表
     */
    TableDataInfo<IcesEquipmentRecordVo> queryPageList(IcesEquipmentRecordBo bo, PageQuery pageQuery);

    /**
     * 查询设备事件日志列表
     */
    List<IcesEquipmentRecordVo> queryList(IcesEquipmentRecordBo bo);

    /**
     * 新增设备事件日志
     */
    Boolean insertByBo(IcesEquipmentRecordBo bo);

    /**
     * 修改设备事件日志
     */
    Boolean updateByBo(IcesEquipmentRecordBo bo);

    /**
     * 校验并批量删除设备事件日志信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
