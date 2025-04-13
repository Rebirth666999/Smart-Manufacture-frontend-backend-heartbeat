package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常参数模板条目对象 ices_exception_param_map
 *
 * @author ruoyi
 * @date 2025-04-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_exception_param_map")
public class IcesExceptionParamMap extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 模板条目ID
     */
    @TableId(value = "expm_id")
    private Long expmId;
    /**
     * 模板条目编码
     */
    private String expmCode;
    /**
     * 异常参数模板
     */
    private String exptCode;
    /**
     * 源字段名
     */
    private String expmNameOrgn;
    /**
     * 源字段编码
     */
    private String expmCodeOrgn;
    /**
     * 目标字段名
     */
    private String expName;
    /**
     * 目标字段编码
     */
    private String expCode;
    /**
     * 默认值
     */
    private String expmDefault;
    /**
     * 转换公式
     */
    private String expmFormula;
    /**
     * 已删除
     */
    private Long expmDelete;
    /**
     * 描述
     */
    private String expmDesc;

}
