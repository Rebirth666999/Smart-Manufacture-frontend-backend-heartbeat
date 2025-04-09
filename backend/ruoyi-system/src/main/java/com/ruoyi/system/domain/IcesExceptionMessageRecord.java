package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常消息记录对象 ices_exception_message_record
 *
 * @author ruoyi
 * @date 2025-04-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_exception_message_record")
public class IcesExceptionMessageRecord extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 消息记录ID
     */
    @TableId(value = "exmr_id")
    private Long exmrId;
    /**
     * 消息记录编码
     */
    private String exmrCode;
    /**
     * 预警配置
     */
    private String exwCode;
    /**
     * 预警记录
     */
    private String exwrCode;
    /**
     * 消息接收人
     */
    private Long exmrRecv;
    /**
     * 内容描述
     */
    private String exmrDesc;
    /**
     * 消息发送时间
     */
    private String exmrTime;
    /**
     * 消息发送结果
     */
    private String exmrResult;
    /**
     * 已删除
     */
    private Long exmrDelete;

}
