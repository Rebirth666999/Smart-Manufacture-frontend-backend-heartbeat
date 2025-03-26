package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 关联-工艺流程原料需求对象 ices_process_material
 *
 * @author ruoyi
 * @date 2025-03-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_process_material")
public class IcesProcessMaterial extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 原料需求ID
     */
    @TableId(value = "pm_id")
    private Long pmId;
    /**
     * 原料需求编码
     */
    private String pmCode;
    /**
     * 所属工艺流程
     */
    private String procCode;
    /**
     * 所用原料
     */
    private String maCode;
    /**
     * 所需原料数量
     */
    private Long pmDemand;
    /**
     * 已删除
     */
    private Long pmDelete;

}
