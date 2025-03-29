package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备操作步骤参数业务对象 ices_equipment_operation_step_param
 *
 * @author ruoyi
 * @date 2025-03-03
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesEquipmentOperationStepParamBo extends BaseEntity {

    /**
     * 设备任务参数ID
     */
    @NotNull(message = "设备任务参数ID不能为空", groups = { EditGroup.class })
    private Long eospaId;

    /**
     * 操作步骤参数编码
     */
    private String eospaCode;

    /**
     * 所属设备操作
     */
    @NotBlank(message = "所属设备操作不能为空", groups = { AddGroup.class, EditGroup.class })
    private String eoCode;

    /**
     * 所属设备操作步骤
     */
    @NotBlank(message = "所属设备操作步骤不能为空", groups = { AddGroup.class, EditGroup.class })
    private String eosCode;

    /**
     * 父级参数
     */
    private String eospaCodeParent;

    /**
     * 参数名称
     */
    @NotBlank(message = "参数名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String eospaName;

    /**
     * 参数类型
     */
    @NotBlank(message = "参数类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String eospaType;

    /**
     * 参数位置
     */
    @NotBlank(message = "参数位置不能为空", groups = { AddGroup.class, EditGroup.class })
    private String eospaPos;

    /**
     * 默认值
     */
    private String eospaValue;

    /**
     * 已删除
     */
    private Long eospaDelete;


}
