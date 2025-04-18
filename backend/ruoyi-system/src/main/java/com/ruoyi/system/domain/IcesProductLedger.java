package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 产品台账对象 ices_product_ledger
 *
 * @author ruoyi
 * @date 2025-04-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_product_ledger")
public class IcesProductLedger extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 台账ID
     */
    @TableId(value = "pl_id")
    private Long plId;
    /**
     * 台账编码
     */
    private String plCode;
    /**
     * 产品
     */
    private String prCode;
    /**
     * 库存量
     */
    private Long plStock;
    /**
     * 已删除
     */
    private Long plDelete;
    /**
     * 描述
     */
    private String plDesc;

}
