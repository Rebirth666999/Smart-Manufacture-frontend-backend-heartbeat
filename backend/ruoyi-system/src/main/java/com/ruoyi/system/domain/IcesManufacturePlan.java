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
 * 生产计划对象 ices_manufacture_plan
 *
 * @author ruoyi
 * @date 2025-03-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_manufacture_plan")
public class IcesManufacturePlan extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 生产计划ID
     */
    @TableId(value = "mp_id")
    private Long mpId;
    /**
     * 生产计划编码
     */
    private String mpCode;
    /**
     * 所属订单
     */
    private String orCode;
    /**
     * 采用工艺流程
     */
    private String procCode;
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
    private Date mpEndPlan;
    /**
     * 实际结束时间
     */
    private Date mpEndReal;
    /**
     * 生产计划优先级
     */
    private Long mpPriority;
    /**
     * 计划产品数量
     */
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
