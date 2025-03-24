package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备任务参数业务对象 ices_device_task_param
 *
 * @author ruoyi
 * @date 2025-03-24
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesDeviceTaskParamBo extends BaseEntity {

    /**
     * 设备任务参数ID
     */
    @NotNull(message = "设备任务参数ID不能为空", groups = { EditGroup.class })
    private Long dtpaId;

    /**
     * 对应操作步骤参数ID
     */
    @NotNull(message = "对应操作步骤参数ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long eospaId;

    /**
     * 所属设备任务ID
     */
    @NotNull(message = "所属设备任务ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long dtId;

    /**
     * 参数值
     */
    @NotBlank(message = "参数值不能为空", groups = { AddGroup.class, EditGroup.class })
    private String dtpaValue;

    /**
     * 已删除
     */
    private Long dtpaDelete;


}
