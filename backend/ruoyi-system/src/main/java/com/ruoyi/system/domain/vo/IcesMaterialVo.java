package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 原料视图对象 ices_material
 *
 * @author ruoyi
 * @date 2025-04-17
 */
@Data
@ExcelIgnoreUnannotated
public class IcesMaterialVo {

    private static final long serialVersionUID = 1L;

    /**
     * 原料ID
     */
    @ExcelProperty(value = "原料ID")
    private Long maId;

    /**
     * 原料编码
     */
    @ExcelProperty(value = "原料编码")
    private String maCode;

    /**
     * 原料名称
     */
    @ExcelProperty(value = "原料名称")
    private String maName;

    /**
     * 占用货位数量
     */
    @ExcelProperty(value = "占用货位数量")
    private Float maOccupy;

    /**
     * 单位
     */
    @ExcelProperty(value = "单位")
    private String maUnit;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long maDelete;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String maDesc;


}
