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
 * 生产任务业务对象 ices_manufacture_task
 *
 * @author ruoyi
 * @date 2025-03-17
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesManufactureTaskBo extends BaseEntity {

    /**
     * 生产任务ID
     */
    @NotNull(message = "生产任务ID不能为空", groups = { EditGroup.class })
    private Long mtId;

    /**
     * 所属生产计划ID
     */
    @NotNull(message = "所属生产计划ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long mpId;

    /**
     * 目标车间ID
     */
    @NotNull(message = "目标车间ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long arId;

    /**
     * 状态代码
     */
    private String mtStat;

    /**
     * 实际开始时间
     */
    private Date mtBegin;

    /**
     * 最晚结束时间
     */
    @NotNull(message = "最晚结束时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date mtEndPlan;

    /**
     * 实际结束时间
     */
    private Date mtEndReal;

    /**
     * 任务优先级
     */
    @NotNull(message = "任务优先级不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long mtPriority;

    /**
     * 计划产品数量
     */
    @NotNull(message = "计划产品数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long mtQtyPlan;

    /**
     * 已完成产品数量
     */
    private Long mtQtyReal;

    /**
     * 已删除
     */
    private Long mtDelete;

    /**
     * 描述
     */
    private String mtDesc;


}
