package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesEquipmentOperation;
import com.ruoyi.system.domain.vo.IcesEquipmentOperationVo;
import com.ruoyi.system.domain.bo.IcesEquipmentOperationBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 设备操作Service接口
 *
 * @author ruoyi
 * @date 2025-03-02
 */
public interface IIcesEquipmentOperationService {

    /**
     * 查询设备操作
     */
    IcesEquipmentOperationVo queryById(Long eoId);

    /**
     * 查询设备操作列表
     */
    TableDataInfo<IcesEquipmentOperationVo> queryPageList(IcesEquipmentOperationBo bo, PageQuery pageQuery);

    /**
     * 查询设备操作列表
     */
    List<IcesEquipmentOperationVo> queryList(IcesEquipmentOperationBo bo);

    /**
     * 新增设备操作
     */
    Boolean insertByBo(IcesEquipmentOperationBo bo);

    /**
     * 修改设备操作
     */
    Boolean updateByBo(IcesEquipmentOperationBo bo);

    /**
     * 校验并批量删除设备操作信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
