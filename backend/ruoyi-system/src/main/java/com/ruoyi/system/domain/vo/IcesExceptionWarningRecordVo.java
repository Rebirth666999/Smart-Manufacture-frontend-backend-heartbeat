package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 异常预警记录视图对象 ices_exception_warning_record
 *
 * @author ruoyi
 * @date 2025-04-09
 */
@Data
@ExcelIgnoreUnannotated
public class IcesExceptionWarningRecordVo {

    private static final long serialVersionUID = 1L;

    /**
     * 预警记录ID
     */
    @ExcelProperty(value = "预警记录ID")
    private Long exwrId;

    /**
     * 预警记录编码
     */
    @ExcelProperty(value = "预警记录编码")
    private String exwrCode;

    /**
     * 预警配置
     */
    @ExcelProperty(value = "预警配置")
    private String exwCode;

    /**
     * 检查时间
     */
    @ExcelProperty(value = "检查时间")
    private String exwrTime;

    /**
     * 检查结果
     */
    @ExcelProperty(value = "检查结果")
    private String exwrResult;

    /**
     * 是否产生预警
     */
    @ExcelProperty(value = "是否产生预警", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ices_yn")
    private String exwrWarning;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long exwrDelete;


}
