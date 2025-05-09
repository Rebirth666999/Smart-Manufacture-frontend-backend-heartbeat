package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常处理日志对象 ices_exception_record_log
 *
 * @author ruoyi
 * @date 2025-04-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_exception_record_log")
public class IcesExceptionRecordLog extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 处理日志ID
     */
    @TableId(value = "exrl_id")
    private Long exrlId;
    /**
     * 处理日志编码
     */
    private String exrlCode;
    /**
     * 所属异常记录
     */
    private String exrCode;
    /**
     * 所属处理任务
     */
    private String exrlTask;
    /**
     * 处理人
     */
    private Long exrlUserHandle;
    /**
     * 处理时间
     */
    private String exrlTime;
    /**
     * 处理意见
     */
    private String exrlResult;
    /**
     * 已删除
     */
    private Long exrlDelete;

}
