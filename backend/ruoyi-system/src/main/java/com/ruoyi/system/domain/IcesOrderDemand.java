package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单所需产品关联对象 ices_order_demand
 *
 * @author ruoyi
 * @date 2025-04-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_order_demand")
public class IcesOrderDemand extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 所需产品ID
     */
    @TableId(value = "od_id")
    private Long odId;
    /**
     * 所需产品编码
     */
    private String odCode;
    /**
     * 所需产品
     */
    private String prCode;
    /**
     * 订单
     */
    private String orCode;
    /**
     * 所需产品数量
     */
    private Float odDemand;
    /**
     * 金额小计
     */
    private Float odPrice;
    /**
     * 定制详情
     */
    private String odCust;
    /**
     * 已删除
     */
    private Long odDelete;

}
