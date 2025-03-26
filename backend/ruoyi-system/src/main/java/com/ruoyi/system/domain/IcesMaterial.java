package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 原料对象 ices_material
 *
 * @author ruoyi
 * @date 2025-02-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_material")
public class IcesMaterial extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 原料ID
     */
    @TableId(value = "ma_id")
    private Long maId;
    /**
     * 原料名称
     */
    private String maName;
    /**
     * 已删除
     */
    private Long maDelete;
    /**
     * 描述
     */
    private String maDesc;
    /**
    *物料编码
     */
    private String maCode;
    /**
    *占用货位数量
     */
    private float maOccupy;
    /**
    *类型
     */
    private String maType;
}
