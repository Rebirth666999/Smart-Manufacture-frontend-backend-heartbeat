package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 原料台账对象 ices_material_record
 *
 * @author ruoyi
 * @date 2025-04-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_material_record")
public class IcesMaterialRecord extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 原料台账ID
     */
    @TableId(value = "mr_id")
    private Long mrId;
    /**
     * 台账编码
     */
    private String mrCode;
    /**
     * 物料
     */
    private String maCode;
    /**
     * 仓库
     */
    private String stCode;
    /**
     * 变动类型
     */
    private String mrType;
    /**
     * 台账状态
     */
    private String mrStat;
    /**
     * 预计变动值
     */
    private Long mrEst;
    /**
     * 实际变动值
     */
    private Long mrReal;
    /**
     * 已删除
     */
    private Long mrDelete;
    /**
     * 描述
     */
    private String mrDesc;

}
