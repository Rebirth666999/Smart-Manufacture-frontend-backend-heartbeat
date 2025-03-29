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
     * 操作步骤编码
     */
    private String eosCode;

    /**
     * 对应原子操作
     */
    private String eaoCode;

    /**
     * 所属设备操作
     */
    @NotBlank(message = "所属设备操作不能为空", groups = { AddGroup.class, EditGroup.class })
    private String eoCode;

    /**
     * 已删除
     */
    private Long eosDelete;

    /**
     * 描述
     */
    private String eosDesc;


}
