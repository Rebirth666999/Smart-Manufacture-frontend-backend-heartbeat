package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备操作对象 ices_equipment_operation
 *
 * @author ruoyi
 * @date 2025-03-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_equipment_operation")
public class IcesEquipmentOperation extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 设备操作ID
     */
    @TableId(value = "eo_id")
    private Long eoId;
    /**
     * 对应模型操作ID
     */
    private Long moId;
    /**
     * 所属设备ID
     */
    private Long eqId;
    /**
     * 后端flowable模型ID
     */
    private String eoModel;
    /**
     * 名称
     */
    private String eoName;
    /**
     * 执行时长
     */
    private Long eoExecTime;
    /**
     * 已删除
     */
    private Long eoDelete;
    /**
     * 描述
     */
    private String eoDesc;

}
