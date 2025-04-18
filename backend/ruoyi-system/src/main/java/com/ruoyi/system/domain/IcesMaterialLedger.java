package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 原料台账对象 ices_material_ledger
 *
 * @author ruoyi
 * @date 2025-04-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_material_ledger")
public class IcesMaterialLedger extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 台账ID
     */
    @TableId(value = "ml_id")
    private Long mlId;
    /**
     * 台账编码
     */
    private String mlCode;
    /**
     * 原料
     */
    private String maCode;
    /**
     * 库存量
     */
    private Float mlStock;
    /**
     * 已删除
     */
    private Long mlDelete;
    /**
     * 描述
     */
    private String mlDesc;

}
