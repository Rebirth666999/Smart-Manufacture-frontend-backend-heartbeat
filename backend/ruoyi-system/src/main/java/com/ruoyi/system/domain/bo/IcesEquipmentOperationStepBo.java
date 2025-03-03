package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备操作步骤业务对象 ices_equipment_operation_step
 *
 * @author ruoyi
 * @date 2025-03-03
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesEquipmentOperationStepBo extends BaseEntity {

    /**
     * 设备操作步骤ID
     */
    @NotNull(message = "设备操作步骤ID不能为空", groups = { EditGroup.class })
    private Long eosId;

    /**
     * 对应原子操作ID
     */
    private Long eaoId;

    /**
     * 所属设备操作ID
     */
    @NotNull(message = "所属设备操作ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long eoId;

    /**
     * 已删除
     */
    private Long eosDelete;

    /**
     * 描述
     */
    private String eosDesc;


}
