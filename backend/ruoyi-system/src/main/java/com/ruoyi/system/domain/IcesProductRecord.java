package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 产品台账对象 ices_product_record
 *
 * @author ruoyi
 * @date 2025-02-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_product_record")
public class IcesProductRecord extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 产品台账ID
     */
    @TableId(value = "prr_id")
    private Long prrId;
    /**
     * 所属产品ID
     */
    private Long prId;
    /**
     * 变动类型
     */
    private String prrType;
    /**
     * 预计变动值
     */
    private Long prrEst;
    /**
     * 实际变动值
     */
    private Long prrReal;
    /**
     * 已删除
     */
    private Long prrDelete;
    /**
     * 描述
     */
    private String prrDesc;

}
