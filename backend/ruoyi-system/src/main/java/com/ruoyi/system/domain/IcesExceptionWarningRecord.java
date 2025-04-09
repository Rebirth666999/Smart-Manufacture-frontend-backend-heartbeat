package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常预警记录对象 ices_exception_warning_record
 *
 * @author ruoyi
 * @date 2025-04-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_exception_warning_record")
public class IcesExceptionWarningRecord extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 预警记录ID
     */
    @TableId(value = "exwr_id")
    private Long exwrId;
    /**
     * 预警记录编码
     */
    private String exwrCode;
    /**
     * 预警配置
     */
    private String exwCode;
    /**
     * 检查时间
     */
    private String exwrTime;
    /**
     * 检查结果
     */
    private String exwrResult;
    /**
     * 是否产生预警
     */
    private String exwrWarning;
    /**
     * 已删除
     */
    private Long exwrDelete;

}
