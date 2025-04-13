package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常参数对象 ices_exception_param
 *
 * @author ruoyi
 * @date 2025-04-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_exception_param")
public class IcesExceptionParam extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 异常参数ID
     */
    @TableId(value = "exp_id")
    private Long expId;
    /**
     * 异常参数编码
     */
    private String expCode;
    /**
     * 所属异常
     */
    private String exCode;
    /**
     * 名称
     */
    private String expName;
    /**
     * 类型
     */
    private String expType;
    /**
     * 已删除
     */
    private Long expDelete;

}
