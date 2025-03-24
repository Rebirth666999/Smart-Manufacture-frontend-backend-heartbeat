package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 关联-设备任务的前序任务对象 ices_device_task_prev
 *
 * @author ruoyi
 * @date 2025-03-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_device_task_prev")
public class IcesDeviceTaskPrev extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 前序任务关联ID
     */
    @TableId(value = "dtpr_id")
    private Long dtprId;
    /**
     * 当前设备任务ID
     */
    private Long dtIdCur;
    /**
     * 前序设备任务ID
     */
    private Long dtIdPrev;
    /**
     * 已删除
     */
    private Long dtprDelete;

}
