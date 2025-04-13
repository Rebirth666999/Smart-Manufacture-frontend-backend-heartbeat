package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常类型对象 ices_exception_type
 *
 * @author ruoyi
 * @date 2025-04-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_exception_type")
public class IcesExceptionType extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 异常类型ID
     */
    @TableId(value = "ext_id")
    private Long extId;
    /**
     * 异常类型编码
     */
    private String extCode;
    /**
     * 上级异常类型
     */
    private String extCodeParent;
    /**
     * 名称
     */
    private String extName;
    /**
     * 已删除
     */
    private Long extDelete;
    /**
     * 描述
     */
    private String extDesc;

}
