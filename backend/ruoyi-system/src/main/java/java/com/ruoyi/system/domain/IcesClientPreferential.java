package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 客户优惠策略对象 ices_client_preferential
 *
 * @author ruoyi
 * @date 2025-03-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_client_preferential")
public class IcesClientPreferential extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 优惠策略ID
     */
    @TableId(value = "cp_id")
    private Long cpId;
    /**
     * 名称
     */
    private String cpName;
    /**
     * 类别
     */
    private String cpType;
    /**
     * 已删除
     */
    private Long cpDelete;
    /**
     * 描述
     */
    private String cpDesc;

}
