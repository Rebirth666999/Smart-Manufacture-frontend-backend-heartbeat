package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 工艺步骤对象 ices_process_step
 *
 * @author ruoyi
 * @date 2025-03-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_process_step")
public class IcesProcessStep extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 工艺步骤ID
     */
    @TableId(value = "ps_id")
    private Long psId;
    /**
     * 所属工艺流程ID
     */
    private Long procId;
    /**
     * 设备模型操作ID
     */
    private Long moId;
    /**
     * 已删除
     */
    private Long psDelete;
    /**
     * 描述
     */
    private String psDesc;

}
