package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常对象 ices_exception
 *
 * @author ruoyi
 * @date 2025-04-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_exception")
public class IcesException extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 异常ID
     */
    @TableId(value = "ex_id")
    private Long exId;
    /**
     * 异常编码
     */
    private String exCode;
    /**
     * 异常类型
     */
    private String extCode;
    /**
     * 名称
     */
    private String exName;
    /**
     * 已删除
     */
    private Long exDelete;
    /**
     * 描述
     */
    private String exDesc;

}
