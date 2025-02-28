package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备模型操作对象 ices_model_operation
 *
 * @author ruoyi
 * @date 2025-02-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_model_operation")
public class IcesModelOperation extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 模型操作ID
     */
    @TableId(value = "mo_id")
    private Long moId;
    /**
     * 所属设备模型ID
     */
    private Long emId;
    /**
     * 名称
     */
    private String moName;
    /**
     * 已删除
     */
    private Long moDelete;
    /**
     * 描述
     */
    private String moDesc;

}
