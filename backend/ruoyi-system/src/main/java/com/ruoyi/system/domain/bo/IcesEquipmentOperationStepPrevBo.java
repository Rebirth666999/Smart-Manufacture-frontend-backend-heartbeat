package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 关联-设备操作步骤的前序步骤业务对象 ices_equipment_operation_step_prev
 *
 * @author ruoyi
 * @date 2025-03-03
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesEquipmentOperationStepPrevBo extends BaseEntity {

    /**
     * 前序步骤关联ID
     */
    @NotNull(message = "前序步骤关联ID不能为空", groups = { EditGroup.class })
    private Long eosprId;

    /**
     * 当前设备操作步骤ID
     */
    @NotNull(message = "当前设备操作步骤ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long eosIdCur;

    /**
     * 前序设备操作步骤ID
     */
    @NotNull(message = "前序设备操作步骤ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long eosIdPrev;

    /**
     * 已删除
     */
    private Long eosprDelete;


}
