package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesEquipmentModel;
import com.ruoyi.system.domain.vo.IcesEquipmentModelVo;
import com.ruoyi.system.domain.bo.IcesEquipmentModelBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 设备模型Service接口
 *
 * @author ruoyi
 * @date 2025-02-18
 */
public interface IIcesEquipmentModelService {

    /**
     * 查询设备模型
     */
    IcesEquipmentModelVo queryById(Long emId);

    /**
     * 查询设备模型列表
     */
    TableDataInfo<IcesEquipmentModelVo> queryPageList(IcesEquipmentModelBo bo, PageQuery pageQuery);

    /**
     * 查询设备模型列表
     */
    List<IcesEquipmentModelVo> queryList(IcesEquipmentModelBo bo);

    /**
     * 新增设备模型
     */
    Boolean insertByBo(IcesEquipmentModelBo bo);

    /**
     * 修改设备模型
     */
    Boolean updateByBo(IcesEquipmentModelBo bo);

    /**
     * 校验并批量删除设备模型信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
