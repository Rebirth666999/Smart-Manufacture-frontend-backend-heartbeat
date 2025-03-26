package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 物料视图对象 ices_material
 *
 * @author ruoyi
 * @date 2025-03-26
 */
@Data
@ExcelIgnoreUnannotated
public class IcesMaterialVo {

    private static final long serialVersionUID = 1L;

    /**
     * 物料ID
     */
    @ExcelProperty(value = "物料ID")
    private Long maId;

    /**
     * 物料编码
     */
    @ExcelProperty(value = "物料编码")
    private String maCode;

    /**
     * 物料名称
     */
    @ExcelProperty(value = "物料名称")
    private String maName;

    /**
     * 物料类型
     */
    @ExcelProperty(value = "物料类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ices_material_type")
    private String maType;

    /**
     * 占用货位数量
     */
    @ExcelProperty(value = "占用货位数量")
    private Long maOccupy;

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
