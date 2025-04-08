package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 异常参数模板条目视图对象 ices_exception_param_map
 *
 * @author ruoyi
 * @date 2025-04-08
 */
@Data
@ExcelIgnoreUnannotated
public class IcesExceptionParamMapVo {

    private static final long serialVersionUID = 1L;

    /**
     * 模板条目ID
     */
    @ExcelProperty(value = "模板条目ID")
    private Long expmId;

    /**
     * 模板条目编码
     */
    @ExcelProperty(value = "模板条目编码")
    private String expmCode;

    /**
     * 异常参数模板
     */
    @ExcelProperty(value = "异常参数模板")
    private String exptCode;

    /**
     * 源字段名
     */
    @ExcelProperty(value = "源字段名")
    private String expmNameOrgn;

    /**
     * 源字段编码
     */
    @ExcelProperty(value = "源字段编码")
    private String expmCodeOrgn;

    /**
     * 目标字段名
     */
    @ExcelProperty(value = "目标字段名")
    private String expName;

    /**
     * 目标字段编码
     */
    @ExcelProperty(value = "目标字段编码")
    private String expCode;

    /**
     * 默认值
     */
    @ExcelProperty(value = "默认值")
    private String expmDefault;

    /**
     * 转换公式
     */
    @ExcelProperty(value = "转换公式")
    private String expmFormula;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long expmDelete;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String expmDesc;


}
