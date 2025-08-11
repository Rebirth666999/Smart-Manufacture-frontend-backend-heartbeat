package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常记录（新）对象 ices_exception_record_new
 *
 * @author ruoyi
 * @date 2025-07-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_exception_record_new")
public class IcesExceptionRecordNew extends BaseEntity {

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
     * 参数(JSON形式)
     */
    private String exrParam;
    /**
     * 状态(数据字典)
     */
    private String exrStat;
    /**
     * 等级(数据字典)
     */
    private String exrLevel;
    /**
     * 异常上报人
     */
    private String exrUserReport;
    /**
     * 当前处理人
     */
    private String exrUserHandle;
    /**
     * 异常解除人
     */
    private String exrUserFinish;
    /**
     * 异常责任人
     */
    private String exrUserResp;
    /**
     * 上报时间
     */
    private String exrCdate;
    /**
     * 影响因子(百分比描述)
     */
    private Float exrImpactFactor;
    /**
     * 影响等级(数据字典)
     */
    private String exrImpactLevel;
    /**
     * 异常执行流程
     */
    private String exrProcess;

    /**
     * 异常图片路径
     */
    private String exrImg;
    /**
     * 处理流程
     */
    private String exrPro;
    /**
     * 异常处理完成图片
     */
    private String exrFinImg;
    /**
     * Ai确认是否是异常
     */
    private String exrAiVer;
    /**
     * Ai确认异常分类
     */
    private String exrAiCls;
    /**
     * 人工确认异常分类
     */
    private String exrHumCls;
    /**
     * 涉及到的部门
     */
    private String exrDep;
    /**
     * 已删除
     */
    private Long exrDelete;

}
