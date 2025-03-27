package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备模型类型对象 ices_equipment_model_type
 *
 * @author ruoyi
 * @date 2025-02-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_equipment_model_type")
public class IcesEquipmentModelType extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 模型类型ID
     */
    @TableId(value = "emt_id")
    private Long emtId;
    /**
     * 模型类型编码
     */
    private String emtCode;
    /**
     * 名称
     */
    private String emtName;
    /**
     * 已删除
     */
    private Long emtDelete;
    /**
     * 描述
     */
    private String emtDesc;

}
