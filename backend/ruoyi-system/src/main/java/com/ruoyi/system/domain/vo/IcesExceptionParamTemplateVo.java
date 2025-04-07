package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 异常参数模板视图对象 ices_exception_param_template
 *
 * @author ruoyi
 * @date 2025-04-07
 */
@Data
@ExcelIgnoreUnannotated
public class IcesExceptionParamTemplateVo {

    private static final long serialVersionUID = 1L;

    /**
     * 参数模板ID
     */
    @ExcelProperty(value = "参数模板ID")
    private Long exptId;

    /**
     * 参数模板编码
     */
    @ExcelProperty(value = "参数模板编码")
    private String exptCode;

    /**
     * 异常
     */
    @ExcelProperty(value = "异常")
    private String exCode;

    /**
     * 异常源
     */
    @ExcelProperty(value = "异常源")
    private String exsCode;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long exptDelete;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String exptDesc;


}
