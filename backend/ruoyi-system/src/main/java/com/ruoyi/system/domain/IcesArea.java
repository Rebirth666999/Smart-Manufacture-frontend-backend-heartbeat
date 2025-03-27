package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 车间对象 ices_area
 *
 * @author ruoyi
 * @date 2025-02-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_area")
public class IcesArea extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 车间ID
     */
    @TableId(value = "ar_id")
    private Long arId;
    /**
     * 车间编号
     */
    private String arCode;
    /**
     * 车间名称
     */
    private String arName;
    /**
     * 已删除
     */
    private Long arDelete;
    /**
     * 描述
     */
    private String arDesc;

}
