package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 关联-工艺步骤的前序步骤对象 ices_process_step_prev
 *
 * @author ruoyi
 * @date 2025-03-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_process_step_prev")
public class IcesProcessStepPrev extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 前序步骤关联ID
     */
    @TableId(value = "psp_id")
    private Long pspId;
    /**
     * 当前工艺步骤ID
     */
    private Long psIdCur;
    /**
     * 前序工艺步骤ID
     */
    private Long psIdPrev;
    /**
     * 已删除
     */
    private Long pspDelete;

}
