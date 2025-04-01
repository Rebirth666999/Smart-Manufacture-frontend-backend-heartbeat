package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 客户贸易信息对象 ices_client_trade
 *
 * @author ruoyi
 * @date 2025-03-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_client_trade")
public class IcesClientTrade extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 贸易信息ID
     */
    @TableId(value = "ct_id")
    private Long ctId;
    /**
     * 贸易信息编码
     */
    private String ctCode;
    /**
     * 客户
     */
    private String clCode;
    /**
     * 收货人
     */
    private String ctName;
    /**
     * 收货地址
     */
    private String ctAddr;
    /**
     * 收货电话
     */
    private String ctPhone;
    /**
     * 国家
     */
    private String ctCountry;
    /**
     * 已删除
     */
    private Long ctDelete;
    /**
     * 描述
     */
    private String ctDesc;

}
