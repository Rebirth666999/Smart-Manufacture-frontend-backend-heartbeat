package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 异常参数视图对象 ices_exception_param
 *
 * @author ruoyi
 * @date 2025-04-07
 */
@Data
@ExcelIgnoreUnannotated
public class IcesExceptionParamVo {

    private static final long serialVersionUID = 1L;

    /**
     * 异常参数ID
     */
    @ExcelProperty(value = "异常参数ID")
    private Long expId;

    /**
     * 异常参数编码
     */
    @ExcelProperty(value = "异常参数编码")
    private String expCode;

    /**
     * 所属异常
     */
    @ExcelProperty(value = "所属异常")
    private String exCode;

    /**
     * 名称
     */
    @ExcelProperty(value = "名称")
    private String expName;

    /**
     * 类型
     */
    @ExcelProperty(value = "类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "exp_type_status")
    private String expType;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long expDelete;


}
