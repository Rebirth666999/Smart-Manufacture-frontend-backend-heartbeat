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
 * 设备任务对象 ices_device_task
 *
 * @author ruoyi
 * @date 2025-03-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_device_task")
public class IcesDeviceTask extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 设备任务ID
     */
    @TableId(value = "dt_id")
    private Long dtId;
    /**
     * 设备ID
     */
    private Long eqId;
    /**
     * 所属生产任务ID
     */
    private Long mtId;
    /**
     * 设备操作ID
     */
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
