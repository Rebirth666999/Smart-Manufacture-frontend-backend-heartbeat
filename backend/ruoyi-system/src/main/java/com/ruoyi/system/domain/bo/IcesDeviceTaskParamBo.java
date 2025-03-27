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
     * 设备任务参数编码
     */
    private String dtpaCode;

    /**
     * 所属生产任务
     */
    @NotNull(message = "所属生产任务不能为空", groups = { EditGroup.class })
    private String mtCode;

    /**
     * 对应操作步骤参数
     */
    @NotBlank(message = "对应操作步骤参数不能为空", groups = { AddGroup.class, EditGroup.class })
    private String eospaCode;

    /**
     * 所属设备任务
     */
    @NotBlank(message = "所属设备任务不能为空", groups = { AddGroup.class, EditGroup.class })
    private String dtCode;

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
