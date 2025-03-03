package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备原子操作对象 ices_equipment_atom_operation
 *
 * @author ruoyi
 * @date 2025-03-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_equipment_atom_operation")
public class IcesEquipmentAtomOperation extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 设备原子操作ID
     */
    @TableId(value = "eao_id")
    private Long eaoId;
    /**
     * 所属设备ID
     */
    private Long eqId;
    /**
     * 原子操作名称
     */
    private String eaoName;
    /**
     * 操作类型
     */
    private String eaoType;
    /**
     * 请求方法
     */
    private String eaoRequestType;
    /**
     * URL
     */
    private String eaoUrl;
    /**
     * 执行时长
     */
    private Long eaoExecTime;
    /**
     * 已删除
     */
    private Long eaoDelete;
    /**
     * 描述
     */
    private String eaoDesc;

}
