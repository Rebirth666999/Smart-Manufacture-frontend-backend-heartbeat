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
     * 当前设备任务ID
     */
    @NotNull(message = "当前设备任务ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long dtIdCur;

    /**
     * 前序设备任务ID
     */
    @NotNull(message = "前序设备任务ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long dtIdPrev;

    /**
     * 已删除
     */
    private Long dtprDelete;


}
