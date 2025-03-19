package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备任务业务对象 ices_device_task
 *
 * @author ruoyi
 * @date 2025-03-19
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesDeviceTaskBo extends BaseEntity {

    /**
     * 设备任务ID
     */
    @NotNull(message = "设备任务ID不能为空", groups = { EditGroup.class })
    private Long dtId;

    /**
     * 所属生产任务ID
     */
    @NotNull(message = "所属生产任务ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long mtId;

    /**
     * 设备操作ID
     */
    @NotNull(message = "设备操作ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long eoId;

    /**
     * 状态代码
     */
    private String dtStat;

    /**
     * 实际开始时间
     */
    private Date dtBegin;

    /**
     * 实际结束时间
     */
    private Date dtEnd;

    /**
     * 已删除
     */
    private Long dtDelete;

    /**
     * 描述
     */
    private String dtDesc;


}
