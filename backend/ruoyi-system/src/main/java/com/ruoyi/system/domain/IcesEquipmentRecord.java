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
 * 设备事件日志对象 ices_equipment_record
 *
 * @author ruoyi
 * @date 2025-03-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_equipment_record")
public class IcesEquipmentRecord extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 事件日志ID
     */
    @TableId(value = "er_id")
    private Long erId;
    /**
     * 关联设备ID
     */
    private Long eqId;
    /**
     * 事件类型代码
     */
    private String erType;
    /**
     * 事件状态代码
     */
    private String erStat;
    /**
     * 事件等级
     */
    private Long erLevel;
    /**
     * 发生时间
     */
    private Date erBegin;
    /**
     * 结束时间
     */
    private Date erEnd;
    /**
     * 已删除
     */
    private Long erDelete;
    /**
     * 描述
     */
    private String erDesc;

}
