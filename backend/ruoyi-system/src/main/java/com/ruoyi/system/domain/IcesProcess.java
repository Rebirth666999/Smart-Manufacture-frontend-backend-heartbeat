package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 工艺流程对象 ices_process
 *
 * @author ruoyi
 * @date 2025-03-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_process")
public class IcesProcess extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 工艺流程ID
     */
    @TableId(value = "proc_id")
    private Long procId;
    /**
     * 目标产品ID
     */
    private Long prId;
    /**
     * 工艺流程名称
     */
    private String procName;
    /**
     * 工艺流程状态
     */
    private String procStat;
    /**
     * 已删除
     */
    private Long procDelete;
    /**
     * 描述
     */
    private String procDesc;

}
