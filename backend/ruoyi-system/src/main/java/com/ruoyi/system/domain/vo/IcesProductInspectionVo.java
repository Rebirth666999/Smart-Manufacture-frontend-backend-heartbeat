package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 产品质检单视图对象 ices_product_inspection
 *
 * @author ruoyi
 * @date 2025-04-19
 */
@Data
@ExcelIgnoreUnannotated
public class IcesProductInspectionVo {

    private static final long serialVersionUID = 1L;

    /**
     * 质检单ID
     */
    @ExcelProperty(value = "质检单ID")
    private Long piId;

    /**
     * 质检单编码
     */
    @ExcelProperty(value = "质检单编码")
    private String piCode;

    /**
     * 批次
     */
    @ExcelProperty(value = "批次")
    private String pbCode;

    /**
     * 负责人
     */
    @ExcelProperty(value = "负责人")
    private String piMan;

    /**
     * 开始日期
     */
    @ExcelProperty(value = "开始日期")
    private String piSdate;

    /**
     * 部门
     */
    @ExcelProperty(value = "部门")
    private String piDept;

    /**
     * 结束日期
     */
    @ExcelProperty(value = "结束日期")
    private String piEdate;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long piDelete;


}
