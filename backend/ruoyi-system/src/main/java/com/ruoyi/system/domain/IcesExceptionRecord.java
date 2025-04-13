package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常记录对象 ices_exception_record
 *
 * @author ruoyi
 * @date 2025-04-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_exception_record")
public class IcesExceptionRecord extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 异常记录ID
     */
    @TableId(value = "exr_id")
    private Long exrId;
    /**
     * 异常记录编码
     */
    private String exrCode;
    /**
     * 异常源
     */
    private String exsCode;
    /**
     * 异常
     */
    private String exCode;
    /**
     * 描述
     */
    private String exrDesc;
    /**
     * 参数
     */
    private String exrParam;
    /**
     * 状态
     */
    private String exrStat;
    /**
     * 等级
     */
    private String exrLevel;
    /**
     * 异常上报人
     */
    private Long exrUserReport;
    /**
     * 当前处理人
     */
    private Long exrUserHandle;
    /**
     * 异常解除人
     */
    private Long exrUserFinish;
    /**
     * 异常责任人
     */
    private Long exrUserResp;
    /**
     * 持续时间
     */
    private Long exrDuration;
    /**
     * 影响因子
     */
    private Float exrImpactFactor;
    /**
     * 影响等级
     */
    private String exrImpactLevel;
    /**
     * 已删除
     */
    private Long exrDelete;

}
