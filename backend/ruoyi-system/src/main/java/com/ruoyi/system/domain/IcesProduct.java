package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 产品对象 ices_product
 *
 * @author ruoyi
 * @date 2025-02-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_product")
public class IcesProduct extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 产品ID
     */
    @TableId(value = "pr_id")
    private Long prId;
    /**
     * 产品名称
     */
    private String prName;
    /**
     * 库存
     */
    private Long prStock;
    /**
     * 已删除
     */
    private Long prDelete;
    /**
     * 描述
     */
    private String prDesc;

}
