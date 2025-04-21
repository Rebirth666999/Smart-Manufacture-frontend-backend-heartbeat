package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 退货记录对象 ices_refund_record
 *
 * @author ruoyi
 * @date 2025-04-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_refund_record")
public class IcesRefundRecord extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 退货记录ID
     */
    @TableId(value = "rr_id")
    private Long rrId;
    /**
     * 退货记录编码
     */
    private String rrCode;
    /**
     * 订单
     */
    private String orCode;
    /**
     * 产品
     */
    private String prCode;
    /**
     * 客户
     */
    private String clCode;
    /**
     * 数量
     */
    private Long rrCount;
    /**
     * 已删除
     */
    private Long rrDelete;
    /**
     * 描述
     */
    private String rrDesc;
    /**
     * 办理人
     */
    private String rrMan;
    /**
     * 办理日期
     */
    private String rrDate;

}
