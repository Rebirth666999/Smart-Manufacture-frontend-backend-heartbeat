package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备任务参数对象 ices_device_task_param
 *
 * @author ruoyi
 * @date 2025-03-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_device_task_param")
public class IcesDeviceTaskParam extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 设备任务参数ID
     */
    @TableId(value = "dtpa_id")
    private Long dtpaId;
    /**
     * 设备任务参数编码
     */
    private String dtpaCode;
    /**
     * 所属生产任务
     */
    private String mtCode;
    /**
     * 对应操作步骤参数
     */
    private String eospaCode;
    /**
     * 所属设备任务
     */
    private String dtCode;
    /**
     * 参数值
     */
    private String dtpaValue;
    /**
     * 已删除
     */
    private Long dtpaDelete;

}
