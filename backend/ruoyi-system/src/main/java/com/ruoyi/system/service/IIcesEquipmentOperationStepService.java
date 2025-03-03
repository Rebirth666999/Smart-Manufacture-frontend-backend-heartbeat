package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesEquipmentOperationStep;
import com.ruoyi.system.domain.vo.IcesEquipmentOperationStepVo;
import com.ruoyi.system.domain.bo.IcesEquipmentOperationStepBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 设备操作步骤Service接口
 *
 * @author ruoyi
 * @date 2025-03-03
 */
public interface IIcesEquipmentOperationStepService {

    /**
     * 查询设备操作步骤
     */
    IcesEquipmentOperationStepVo queryById(Long eosId);

    /**
     * 查询设备操作步骤列表
     */
    TableDataInfo<IcesEquipmentOperationStepVo> queryPageList(IcesEquipmentOperationStepBo bo, PageQuery pageQuery);

    /**
     * 查询设备操作步骤列表
     */
    List<IcesEquipmentOperationStepVo> queryList(IcesEquipmentOperationStepBo bo);

    /**
     * 新增设备操作步骤
     */
    Boolean insertByBo(IcesEquipmentOperationStepBo bo);

    /**
     * 修改设备操作步骤
     */
    Boolean updateByBo(IcesEquipmentOperationStepBo bo);

    /**
     * 校验并批量删除设备操作步骤信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
