package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesEquipmentModelType;
import com.ruoyi.system.domain.vo.IcesEquipmentModelTypeVo;
import com.ruoyi.system.domain.bo.IcesEquipmentModelTypeBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 设备模型类型Service接口
 *
 * @author ruoyi
 * @date 2025-02-18
 */
public interface IIcesEquipmentModelTypeService {

    /**
     * 查询设备模型类型
     */
    IcesEquipmentModelTypeVo queryById(Long emtId);

    /**
     * 查询设备模型类型列表
     */
    TableDataInfo<IcesEquipmentModelTypeVo> queryPageList(IcesEquipmentModelTypeBo bo, PageQuery pageQuery);

    /**
     * 查询设备模型类型列表
     */
    List<IcesEquipmentModelTypeVo> queryList(IcesEquipmentModelTypeBo bo);

    /**
     * 新增设备模型类型
     */
    Boolean insertByBo(IcesEquipmentModelTypeBo bo);

    /**
     * 修改设备模型类型
     */
    Boolean updateByBo(IcesEquipmentModelTypeBo bo);

    /**
     * 校验并批量删除设备模型类型信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
