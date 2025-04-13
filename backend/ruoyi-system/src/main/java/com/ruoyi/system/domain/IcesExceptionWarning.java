package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常预警配置对象 ices_exception_warning
 *
 * @author ruoyi
 * @date 2025-04-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_exception_warning")
public class IcesExceptionWarning extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 预警配置ID
     */
    @TableId(value = "exw_id")
    private Long exwId;
    /**
     * 预警配置编码
     */
    private String exwCode;
    /**
     * 异常
     */
    private String exCode;
    /**
     * 异常源
     */
    private String exsCode;
    /**
     * 时间表达式
     */
    private String exwFormula;
    /**
     * 消息接收人
     */
    private Long exwRecv;
    /**
     * 消息通道
     */
    private String exwTunnel;
    /**
     * 消息模板
     */
    private String exwTemplate;
    /**
     * 是否触发源系统预警
     */
    private String exwWarningOrgn;
    /**
     * 是否触发源系统处理
     */
    private String exwHandleOrgn;
    /**
     * 已删除
     */
    private Long exwDelete;

}
