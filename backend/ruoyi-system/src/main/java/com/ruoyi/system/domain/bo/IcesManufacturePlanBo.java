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
 * 生产计划业务对象 ices_manufacture_plan
 *
 * @author ruoyi
 * @date 2025-03-17
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesManufacturePlanBo extends BaseEntity {

    /**
     * 生产计划ID
     */
    @NotNull(message = "生产计划ID不能为空", groups = { EditGroup.class })
    private Long mpId;

    /**
     * 所属订单ID
     */
    @NotNull(message = "所属订单ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long orId;

    /**
     * 采用工艺流程ID
     */
    @NotNull(message = "采用工艺流程ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long procId;

    /**
     * 状态代码
     */
    private String mpStat;

    /**
     * 实际开始时间
     */
    private Date mpBegin;

    /**
     * 最晚结束时间
     */
    @NotNull(message = "最晚结束时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date mpEndPlan;

    /**
     * 实际结束时间
     */
    private Date mpEndReal;

    /**
     * 生产计划优先级
     */
    @NotNull(message = "生产计划优先级不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long mpPriority;

    /**
     * 计划产品数量
     */
    @NotNull(message = "计划产品数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Float mpQtyPlan;

    /**
     * 已完成产品数量
     */
    private Float mpQtyReal;

    /**
     * 已删除
     */
    private Long mpDelete;

    /**
     * 描述
     */
    private String mpDesc;


}
