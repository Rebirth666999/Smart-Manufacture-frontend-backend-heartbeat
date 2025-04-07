package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常参数模板对象 ices_exception_param_template
 *
 * @author ruoyi
 * @date 2025-04-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_exception_param_template")
public class IcesExceptionParamTemplate extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 参数模板ID
     */
    @TableId(value = "expt_id")
    private Long exptId;
    /**
     * 参数模板编码
     */
    private String exptCode;
    /**
     * 异常
     */
    private String exCode;
    /**
     * 异常源
     */
    private String exsCode;
    /**
     * 已删除
     */
    private Long exptDelete;
    /**
     * 描述
     */
    private String exptDesc;

}
