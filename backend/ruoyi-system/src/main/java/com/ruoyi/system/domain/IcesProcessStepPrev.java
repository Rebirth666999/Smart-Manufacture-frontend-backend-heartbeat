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
     * 跨轮前序步骤关联编码
     */
    private Long pspCode;
    /**
     * 所属工艺流程
     */
    private String procCode;
    /**
     * 当前工艺步骤
     */
    private String psCodeCur;
    /**
     * 跨轮次前序工艺步骤
     */
    private String psCodePrev;
    /**
     * 已删除
     */
    private Long pspDelete;

}
