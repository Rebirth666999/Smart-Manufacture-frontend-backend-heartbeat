package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备模型对象 ices_equipment_model
 *
 * @author ruoyi
 * @date 2025-02-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_equipment_model")
public class IcesEquipmentModel extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 设备模型ID
     */
    @TableId(value = "em_id")
    private Long emId;
    /**
     * 所属模型类型ID
     */
    private Long emtId;
    /**
     * 名称
     */
    private String emName;
    /**
     * 状态
     */
    private String emStat;
    /**
     * 已删除
     */
    private Long emDelete;
    /**
     * 描述
     */
    private String emDesc;

}
