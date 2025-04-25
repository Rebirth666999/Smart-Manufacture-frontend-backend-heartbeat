package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 生产计划(主)对象 ices_manufacture_plan_main
 *
 * @author ruoyi
 * @date 2025-04-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_manufacture_plan_main")
public class IcesManufacturePlanMain extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 生产计划ID
     */
    @TableId(value = "mpm_id")
    private Long mpmId;
    /**
     * 生产计划编码
     */
    private String mpmCode;
    /**
     * 所属订单
     */
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
