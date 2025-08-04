package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 异常记录（新）视图对象 ices_exception_record_new
 *
 * @author ruoyi
 * @date 2025-07-30
 */
@Data
@ExcelIgnoreUnannotated
public class IcesExceptionRecordNewVo {

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
    private String exsCode;

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
     * 参数(JSON形式)
     */
    @ExcelProperty(value = "参数(JSON形式)")
    private String exrParam;

    /**
     * 状态(数据字典)
     */
    @ExcelProperty(value = "状态(数据字典)", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ices_exception_record_status")
    private String exrStat;

    /**
     * 等级(数据字典)
     */
    @ExcelProperty(value = "等级(数据字典)", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ices_exception_record_level")
    private String exrLevel;

    /**
     * 异常上报人
     */
    @ExcelProperty(value = "异常上报人")
    private String exrUserReport;

    /**
     * 当前处理人
     */
    @ExcelProperty(value = "当前处理人")
    private String exrUserHandle;

    /**
     * 异常解除人
     */
    @ExcelProperty(value = "异常解除人")
    private String exrUserFinish;

    /**
     * 异常责任人
     */
    @ExcelProperty(value = "异常责任人")
    private String exrUserResp;

    /**
     * 上报时间
     */
    @ExcelProperty(value = "上报时间")
    private String exrCdate;

    /**
     * 影响因子(百分比描述)
     */
    @ExcelProperty(value = "影响因子(百分比描述)")
    private Long exrImpactFactor;

    /**
     * 影响等级(数据字典)
     */
    @ExcelProperty(value = "影响等级(数据字典)", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ices_exception_record_impact_level")
    private String exrImpactLevel;

    /**
     * 异常执行流程
     */
    @ExcelProperty(value = "异常执行流程")
    private String exrProcess;

    /**
     * 持续时间(秒)
     */
    @ExcelProperty(value = "持续时间(秒)")
    private Long exrDuration;

    /**
     * 异常图片路径
     */
    @ExcelProperty(value = "异常图片路径")
    private String exrImg;

    /**
     * 异常处理完成图片
     */
    @ExcelProperty(value = "异常处理完成图片")
    private String exrFinImg;

    /**
     * Ai确认是否是异常
     */
    @ExcelProperty(value = "Ai确认是否是异常")
    private String exrAiVer;

    /**
     * Ai确认异常分类
     */
    @ExcelProperty(value = "Ai确认异常分类")
    private String exrAiCls;

    /**
     * 人工确认异常分类
     */
    @ExcelProperty(value = "人工确认异常分类")
    private String exrHumCls;

    /**
     * 涉及到的部门
     */
    @ExcelProperty(value = "涉及到的部门")
    private String exrDep;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long exrDelete;


}
