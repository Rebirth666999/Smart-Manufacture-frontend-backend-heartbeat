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
 * 原料对象 ices_material
 *
 * @author ruoyi
 * @date 2025-02-06
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
     * 创建时间
     */
    private Date maCreateTime;
    /**
     * 修改时间
     */
    private Date maUpdateTime;
    /**
     * 描述
     */
    private String maDesc;

}
