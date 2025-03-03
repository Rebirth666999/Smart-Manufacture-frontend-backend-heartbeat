package com.ruoyi.system.service;

import com.ruoyi.system.domain.IcesEquipmentOperationStepParam;
import com.ruoyi.system.domain.vo.IcesEquipmentOperationStepParamVo;
import com.ruoyi.system.domain.bo.IcesEquipmentOperationStepParamBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 设备操作步骤参数Service接口
 *
 * @author ruoyi
 * @date 2025-03-03
 */
public interface IIcesEquipmentOperationStepParamService {

    /**
     * 查询设备操作步骤参数
     */
    IcesEquipmentOperationStepParamVo queryById(Long eospaId);

    /**
     * 查询设备操作步骤参数列表
     */
    TableDataInfo<IcesEquipmentOperationStepParamVo> queryPageList(IcesEquipmentOperationStepParamBo bo, PageQuery pageQuery);

    /**
     * 查询设备操作步骤参数列表
     */
    List<IcesEquipmentOperationStepParamVo> queryList(IcesEquipmentOperationStepParamBo bo);

    /**
     * 新增设备操作步骤参数
     */
    Boolean insertByBo(IcesEquipmentOperationStepParamBo bo);

    /**
     * 修改设备操作步骤参数
     */
    Boolean updateByBo(IcesEquipmentOperationStepParamBo bo);

    /**
     * 校验并批量删除设备操作步骤参数信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
