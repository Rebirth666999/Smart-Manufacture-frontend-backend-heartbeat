package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 关联-客户等级对应的优惠策略对象 ices_client_level_preferential
 *
 * @author ruoyi
 * @date 2025-03-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_client_level_preferential")
public class IcesClientLevelPreferential extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 对应关系ID
     */
    @TableId(value = "clp_id")
    private Long clpId;
    /**
     * 对应关系编码
     */
    private String clpCode;
    /**
     * 客户等级
     */
    private String cllCode;
    /**
     * 优惠策略
     */
    private String cpCode;
    /**
     * 已删除
     */
    private Long clpDelete;

}
