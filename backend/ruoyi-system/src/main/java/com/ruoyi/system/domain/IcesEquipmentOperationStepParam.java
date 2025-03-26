package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备操作步骤参数对象 ices_equipment_operation_step_param
 *
 * @author ruoyi
 * @date 2025-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_equipment_operation_step_param")
public class IcesEquipmentOperationStepParam extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 设备任务参数ID
     */
    @TableId(value = "eospa_id")
    private Long eospaId;
    /**
     * 操作步骤参数编码
     */
    private String eospaCode;
    /**
     * 所属设备操作
     */
    private String eoCode;
    /**
     * 所属设备操作步骤
     */
    private String eosCode;
    /**
     * 父级参数
     */
    private String eospaCodeParent;
    /**
     * 参数名称
     */
    private String eospaName;
    /**
     * 参数类型
     */
    private String eospaType;
    /**
     * 参数位置
     */
    private String eospaPos;
    /**
     * 默认值
     */
    private String eospaValue;
    /**
     * 已删除
     */
    private Long eospaDelete;

}
