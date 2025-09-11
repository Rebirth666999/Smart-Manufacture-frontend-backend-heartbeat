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
 * 设备对象 ices_equipment
 *
 * @author ruoyi
 * @date 2025-02-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_equipment")
public class IcesEquipment extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 设备ID
     */
    @TableId(value = "eq_id")
    private Long eqId;
    /**
     * 设备编码
     */
    private String eqCode;
    /**
     * 所属车间
     */
    private String arCode;
    /**
     * 所属设备模型
     */
    private String emCode;
    /**
     * 名称
     */
    private String eqName;
    /**
     * 状态代码
     */
    private String eqStat;
    /**
     * 采购时间
     */
    private Date eqIntroduceTime;
    /**
     * 上次通讯时间
     */
    private Date eqCommunicateTime;
    /**
     * IP地址
     */
    private String eqIp;
    /* 设备测试用flaskip*/
    private String eqFlaskIp;
    /**
     * 已删除
     */
    private Long eqDelete;
    /**
     * 描述
     */
    private String eqDesc;

    /**
     * 创建人
     */
    private String eqCman;
    /**
     * 创建时间
     */
    private String eqCdate;
    /**
     * 发布人
     */
    private String eqRman;
    /**
     * 发布时间
     */
    private String eqRdate;
    /**
     * 修改人
     */
    private String eqMman;
    /**
     * 修改时间
     */
    private String eqMdate;

}
