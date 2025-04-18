package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 仓库产品库存对象 ices_product_stock
 *
 * @author ruoyi
 * @date 2025-04-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_product_stock")
public class IcesProductStock extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 仓库产品库存ID
     */
    @TableId(value = "pss_id")
    private Long pssId;
    /**
     * 仓库产品库存编码
     */
    private String pssCode;
    /**
     * 仓库
     */
    private String prsCode;
    /**
     * 产品
     */
    private String prCode;
    /**
     * 库存量
     */
    private Float pssStock;
    /**
     * 已删除
     */
    private Long pssDelete;

}
