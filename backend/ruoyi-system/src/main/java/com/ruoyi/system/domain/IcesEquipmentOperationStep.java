package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备操作步骤对象 ices_equipment_operation_step
 *
 * @author ruoyi
 * @date 2025-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_equipment_operation_step")
public class IcesEquipmentOperationStep extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 设备操作步骤ID
     */
    @TableId(value = "eos_id")
    private Long eosId;
    /**
     * 对应原子操作ID
     */
    private Long eaoId;
    /**
     * 所属设备操作ID
     */
    private Long eoId;
    /**
     * 已删除
     */
    private Long eosDelete;
    /**
     * 描述
     */
    private String eosDesc;

}
