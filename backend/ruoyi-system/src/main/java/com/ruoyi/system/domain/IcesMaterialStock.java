package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 车间原料库存对象 ices_material_stock
 *
 * @author ruoyi
 * @date 2025-02-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_material_stock")
public class IcesMaterialStock extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 车间原料库存ID
     */
    @TableId(value = "ms_id")
    private Long msId;
    /**
     * 车间ID
     */
    private Long arId;
    /**
     * 原料ID
     */
    private Long maId;
    /**
     * 库存
     */
    private Long msStock;
    /**
     * 已删除
     */
    private Long msDelete;

}
