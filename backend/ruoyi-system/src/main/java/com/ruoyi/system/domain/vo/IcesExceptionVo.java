package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 异常视图对象 ices_exception
 *
 * @author ruoyi
 * @date 2025-04-07
 */
@Data
@ExcelIgnoreUnannotated
public class IcesExceptionVo {

    private static final long serialVersionUID = 1L;

    /**
     * 异常ID
     */
    @ExcelProperty(value = "异常ID")
    private Long exId;

    /**
     * 异常编码
     */
    @ExcelProperty(value = "异常编码")
    private String exCode;

    /**
     * 异常类型
     */
    @ExcelProperty(value = "异常类型")
    private String extCode;

    /**
     * 名称
     */
    @ExcelProperty(value = "名称")
    private String exName;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long exDelete;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String exDesc;


}
