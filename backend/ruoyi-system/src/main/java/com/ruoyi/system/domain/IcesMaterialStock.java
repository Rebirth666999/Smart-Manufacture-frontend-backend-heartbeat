package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 仓库原料库存对象 ices_material_stock
 *
 * @author ruoyi
 * @date 2025-04-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_material_stock")
public class IcesMaterialStock extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 仓库原料库存ID
     */
    @TableId(value = "mss_id")
    private Long mssId;
    /**
     * 仓库原料库存编码
     */
    private String mssCode;
    /**
     * 仓库
     */
    private String msCode;
    /**
     * 物料
     */
    private String maCode;
    /**
     * 库存
     */
    private Long mssStock;
    /**
     * 已删除
     */
    private Long mssDelete;

}
