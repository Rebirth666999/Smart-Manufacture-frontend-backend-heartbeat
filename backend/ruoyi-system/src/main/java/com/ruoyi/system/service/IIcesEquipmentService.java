package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesEquipment;
import com.ruoyi.system.domain.vo.IcesEquipmentVo;
import com.ruoyi.system.domain.bo.IcesEquipmentBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 设备Service接口
 *
 * @author ruoyi
 * @date 2025-02-28
 */
public interface IIcesEquipmentService {

    /**
     * 查询设备
     */
    IcesEquipmentVo queryById(Long eqId);

    /**
     * 查询设备列表
     */
    TableDataInfo<IcesEquipmentVo> queryPageList(IcesEquipmentBo bo, PageQuery pageQuery);

    /**
     * 查询设备列表
     */
    List<IcesEquipmentVo> queryList(IcesEquipmentBo bo);

    /**
     * 新增设备
     */
    Boolean insertByBo(IcesEquipmentBo bo);

    /**
     * 修改设备
     */
    Boolean updateByBo(IcesEquipmentBo bo);

    /**
     * 校验并批量删除设备信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
