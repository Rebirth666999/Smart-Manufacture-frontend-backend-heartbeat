package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 关联-设备操作步骤的前序步骤对象 ices_equipment_operation_step_prev
 *
 * @author ruoyi
 * @date 2025-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_equipment_operation_step_prev")
public class IcesEquipmentOperationStepPrev extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 前序步骤关联ID
     */
    @TableId(value = "eospr_id")
    private Long eosprId;
    /**
     * 前序步骤关联编码
     */
    private String eosprCode;
    /**
     * 所属设备操作
     */
    private String eoCode;
    /**
     * 当前设备操作步骤
     */
    private String eosCodeCur;
    /**
     * 前序设备操作步骤
     */
    private String eosCodePrev;
    /**
     * 已删除
     */
    private Long eosprDelete;

}
