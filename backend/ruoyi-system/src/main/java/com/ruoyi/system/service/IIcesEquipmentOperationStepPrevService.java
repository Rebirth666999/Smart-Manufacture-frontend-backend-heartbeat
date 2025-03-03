package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesEquipmentOperationStepPrev;
import com.ruoyi.system.domain.vo.IcesEquipmentOperationStepPrevVo;
import com.ruoyi.system.domain.bo.IcesEquipmentOperationStepPrevBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 关联-设备操作步骤的前序步骤Service接口
 *
 * @author ruoyi
 * @date 2025-03-03
 */
public interface IIcesEquipmentOperationStepPrevService {

    /**
     * 查询关联-设备操作步骤的前序步骤
     */
    IcesEquipmentOperationStepPrevVo queryById(Long eosprId);

    /**
     * 查询关联-设备操作步骤的前序步骤列表
     */
    TableDataInfo<IcesEquipmentOperationStepPrevVo> queryPageList(IcesEquipmentOperationStepPrevBo bo, PageQuery pageQuery);

    /**
     * 查询关联-设备操作步骤的前序步骤列表
     */
    List<IcesEquipmentOperationStepPrevVo> queryList(IcesEquipmentOperationStepPrevBo bo);

    /**
     * 新增关联-设备操作步骤的前序步骤
     */
    Boolean insertByBo(IcesEquipmentOperationStepPrevBo bo);

    /**
     * 修改关联-设备操作步骤的前序步骤
     */
    Boolean updateByBo(IcesEquipmentOperationStepPrevBo bo);

    /**
     * 校验并批量删除关联-设备操作步骤的前序步骤信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
