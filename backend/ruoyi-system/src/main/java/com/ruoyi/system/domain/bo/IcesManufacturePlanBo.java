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
     * 生产计划编码
     */
    private String mpCode;

    /**
     * 所属订单
     */
    @NotBlank(message = "所属订单不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orCode;

    /**
     * 产品需求
     */
    @NotBlank(message = "产品需求不能为空", groups = { AddGroup.class, EditGroup.class })
    private String odCode;

    /**
     * 所属生产计划
     */
    private String mpmCode;

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

    /**
     * 创建人
     */
    private String mpCman;
    /**
     * 创建时间
     */
    private String mpCdate;
    /**
     * 发布人
     */
    private String mpRman;
    /**
     * 发布时间
     */
    private String mpRdate;
    /**
     * 修改人
     */
    private String mpMman;
    /**
     * 修改时间
     */
    private String mpMdate;

}
