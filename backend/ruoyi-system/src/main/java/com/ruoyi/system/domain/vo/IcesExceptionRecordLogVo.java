package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 异常处理日志视图对象 ices_exception_record_log
 *
 * @author ruoyi
 * @date 2025-04-09
 */
@Data
@ExcelIgnoreUnannotated
public class IcesExceptionRecordLogVo {

    private static final long serialVersionUID = 1L;

    /**
     * 处理日志ID
     */
    @ExcelProperty(value = "处理日志ID")
    private Long exrlId;

    /**
     * 处理日志编码
     */
    @ExcelProperty(value = "处理日志编码")
    private String exrlCode;

    /**
     * 所属异常记录
     */
    @ExcelProperty(value = "所属异常记录")
    private String exrCode;

    /**
     * 处理人
     */
    @ExcelProperty(value = "处理人")
    private Long exrlUserHandle;

    /**
     * 处理时间
     */
    @ExcelProperty(value = "处理时间")
    private String exrlTime;

    /**
     * 处理意见
     */
    @ExcelProperty(value = "处理意见")
    private String exrlResult;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long exrlDelete;


}
