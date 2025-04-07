package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 异常类型视图对象 ices_exception_type
 *
 * @author ruoyi
 * @date 2025-04-07
 */
@Data
@ExcelIgnoreUnannotated
public class IcesExceptionTypeVo {

    private static final long serialVersionUID = 1L;

    /**
     * 异常类型ID
     */
    @ExcelProperty(value = "异常类型ID")
    private Long extId;

    /**
     * 异常类型编码
     */
    @ExcelProperty(value = "异常类型编码")
    private String extCode;

    /**
     * 上级异常类型
     */
    @ExcelProperty(value = "上级异常类型")
    private String extCodeParent;

    /**
     * 名称
     */
    @ExcelProperty(value = "名称")
    private String extName;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long extDelete;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String extDesc;


}
