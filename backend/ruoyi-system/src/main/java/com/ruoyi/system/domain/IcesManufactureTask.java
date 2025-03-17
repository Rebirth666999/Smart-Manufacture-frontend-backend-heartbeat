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
 * 生产任务对象 ices_manufacture_task
 *
 * @author ruoyi
 * @date 2025-03-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_manufacture_task")
public class IcesManufactureTask extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 生产任务ID
     */
    @TableId(value = "mt_id")
    private Long mtId;
    /**
     * 所属生产计划ID
     */
    private Long mpId;
    /**
     * 目标车间ID
     */
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
    private Date mtEndPlan;
    /**
     * 实际结束时间
     */
    private Date mtEndReal;
    /**
     * 任务优先级
     */
    private Long mtPriority;
    /**
     * 计划产品数量
     */
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
