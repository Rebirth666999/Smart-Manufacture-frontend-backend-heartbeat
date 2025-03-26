package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单对象 ices_order
 *
 * @author ruoyi
 * @date 2025-03-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_order")
public class IcesOrder extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 订单ID
     */
    @TableId(value = "or_id")
    private Long orId;
    /**
     * 订单编码
     */
    private String orCode;
    /**
     * 所需产品ID
     */
    private String maCode;
    /**
     * 客户ID
     */
    private String clCode;
    /**
     * 订单名称
     */
    private String orName;
    /**
     * 状态代码
     */
    private String orStat;
    /**
     * 所需产品数量
     */
    private Long orDemand;
    /**
     * 订单优先级
     */
    private Long orPriority;
    /**
     * 截止时间
     */
    private Date orDeadline;
    /**
     * 总价
     */
    private Long orPrice;
    /**
     * 收货人
     */
    private String orRecv;
    /**
     * 收货地址
     */
    private String orAddr;
    /**
     * 已删除
     */
    private Long orDelete;
    /**
     * 描述
     */
    private String orDesc;

}
