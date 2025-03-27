package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 关联-设备任务的前序任务业务对象 ices_device_task_prev
 *
 * @author ruoyi
 * @date 2025-03-24
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesDeviceTaskPrevBo extends BaseEntity {

    /**
     * 前序任务关联ID
     */
    @NotNull(message = "前序任务关联ID不能为空", groups = { EditGroup.class })
    private Long dtprId;

    /**
     * 前序任务关联编码
     */
    private String dtprCode;

    /**
     * 所属生产任务
     */
    @NotBlank(message = "所属生产任务不能为空", groups = { AddGroup.class, EditGroup.class })
    private String mtCode;

    /**
     * 当前设备任务
     */
    @NotBlank(message = "当前设备任务不能为空", groups = { AddGroup.class, EditGroup.class })
    private String dtCodeCur;

    /**
     * 前序设备任务
     */
    @NotBlank(message = "前序设备任务不能为空", groups = { AddGroup.class, EditGroup.class })
    private String dtCodePrev;

    /**
     * 已删除
     */
    private Long dtprDelete;


}
