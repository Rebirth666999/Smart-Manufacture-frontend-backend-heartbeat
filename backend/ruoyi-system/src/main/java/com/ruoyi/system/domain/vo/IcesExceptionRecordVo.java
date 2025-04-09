package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 异常记录视图对象 ices_exception_record
 *
 * @author ruoyi
 * @date 2025-04-09
 */
@Data
@ExcelIgnoreUnannotated
public class IcesExceptionRecordVo {

    private static final long serialVersionUID = 1L;

    /**
     * 异常记录ID
     */
    @ExcelProperty(value = "异常记录ID")
    private Long exrId;

    /**
     * 异常记录编码
     */
    @ExcelProperty(value = "异常记录编码")
    private String exrCode;

    /**
     * 异常源
     */
    @ExcelProperty(value = "异常源")
    private String excCode;

    /**
     * 异常
     */
    @ExcelProperty(value = "异常")
    private String exCode;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String exrDesc;

    /**
     * 参数
     */
    @ExcelProperty(value = "参数")
    private String exrParam;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ices_exception_record_status")
    private String exrStat;

    /**
     * 等级
     */
    @ExcelProperty(value = "等级", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ices_exception_record_level")
    private String exrLevel;

    /**
     * 异常上报人
     */
    @ExcelProperty(value = "异常上报人")
    private Long exrUserReport;

    /**
     * 当前处理人
     */
    @ExcelProperty(value = "当前处理人")
    private Long exrUserHandle;

    /**
     * 异常解除人
     */
    @ExcelProperty(value = "异常解除人")
    private Long exrUserFinish;

    /**
     * 异常责任人
     */
    @ExcelProperty(value = "异常责任人")
    private Long exrUserResp;

    /**
     * 持续时间
     */
    @ExcelProperty(value = "持续时间")
    private Long exrDuration;

    /**
     * 影响因子
     */
    @ExcelProperty(value = "影响因子")
    private Long exrImpactFactor;

    /**
     * 影响等级
     */
    @ExcelProperty(value = "影响等级")
    private String exrImpactLevel;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long exrDelete;


}
