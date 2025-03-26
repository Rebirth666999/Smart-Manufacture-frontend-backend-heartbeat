package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备操作业务对象 ices_equipment_operation
 *
 * @author ruoyi
 * @date 2025-03-02
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesEquipmentOperationBo extends BaseEntity {

    /**
     * 设备操作ID
     */
    @NotNull(message = "设备操作ID不能为空", groups = { EditGroup.class })
    private Long eoId;

    /**
     * 对应模型操作ID
     */
    @NotNull(message = "对应模型操作ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long moId;

    /**
     * 所属设备ID
     */
    @NotNull(message = "所属设备ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long eqId;

    /**
     * 名称
     */
    private String eoName;

    /**
     * 执行时长
     */
    private Float eoExecTime;

    /**
     * 已删除
     */
    private Long eoDelete;

    /**
     * 描述
     */
    private String eoDesc;


}
