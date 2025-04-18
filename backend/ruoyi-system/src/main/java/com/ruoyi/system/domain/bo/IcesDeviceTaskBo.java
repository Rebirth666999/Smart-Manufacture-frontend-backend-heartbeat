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
     * 设备任务编码
     */
    private String dtCode;

    /**
     * 设备
     */
    @NotBlank(message = "设备不能为空", groups = { AddGroup.class, EditGroup.class })
    private String eqCode;

    /**
     * 所属生产任务
     */
    @NotBlank(message = "所属生产任务不能为空", groups = { AddGroup.class, EditGroup.class })
    private String mtCode;

    /**
     * 图元ID
     */
    private String dtModel;

    /**
     * 设备操作
     */
    @NotBlank(message = "设备操作不能为空", groups = { AddGroup.class, EditGroup.class })
    private String eoCode;

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
