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
 * 生产计划(主)业务对象 ices_manufacture_plan_main
 *
 * @author ruoyi
 * @date 2025-04-25
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesManufacturePlanMainBo extends BaseEntity {

    /**
     * 生产计划ID
     */
    @NotNull(message = "生产计划ID不能为空", groups = { EditGroup.class })
    private Long mpmId;

    /**
     * 生产计划编码
     */
    private String mpmCode;

    /**
     * 所属订单
     */
    @NotBlank(message = "所属订单不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orCode;

    /**
     * 状态
     */
    private String mpmStat;

    /**
     * 实际开始时间
     */
    private Date mpmBegin;

    /**
     * 最晚结束时间
     */
    @NotNull(message = "最晚结束时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date mpmEndPlan;

    /**
     * 实际结束时间
     */
    private Date mpmEndReal;

    /**
     * 已删除
     */
    private Long mpmDelete;

    /**
     * 创建人
     */
    private String mpmCman;

    /**
     * 创建日期
     */
    private String mpmCdate;

    /**
     * 修改人
     */
    private String mpmMman;

    /**
     * 修改日期
     */
    private String mpmMdate;


}
