package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 实际产品批次编码对象 ices_product_batch
 *
 * @author ruoyi
 * @date 2025-04-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_product_batch")
public class IcesProductBatch extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 产品批次ID
     */
    @TableId(value = "pb_id")
    private Long pbId;
    /**
     * 产品批次编码
     */
    private String pbCode;
    /**
     * 订单产品需求
     */
    private String odCode;
    /**
     * 批次编号
     */
    private String pbBatch;
    /**
     * 起始号码
     */
    private Long pbStart;
    /**
     * 终止号码
     */
    private Long pbEnd;
    /**
     * 已删除
     */
    private Long pbDelete;

}
