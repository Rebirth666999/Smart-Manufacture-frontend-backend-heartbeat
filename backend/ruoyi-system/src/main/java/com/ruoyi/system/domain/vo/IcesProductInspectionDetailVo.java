package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 产品质检单明细视图对象 ices_product_inspection_detail
 *
 * @author ruoyi
 * @date 2025-04-19
 */
@Data
@ExcelIgnoreUnannotated
public class IcesProductInspectionDetailVo {

    private static final long serialVersionUID = 1L;

    /**
     * 质检单明细ID
     */
    @ExcelProperty(value = "质检单明细ID")
    private Long pidId;

    /**
     * 质检单明细编码
     */
    @ExcelProperty(value = "质检单明细编码")
    private String pidCode;

    /**
     * 所属质检单
     */
    @ExcelProperty(value = "所属质检单")
    private String piCode;

    /**
     * 产品类型
     */
    @ExcelProperty(value = "产品类型")
    private String prCode;

    /**
     * 产品编码
     */
    @ExcelProperty(value = "产品编码")
    private String pidBatchNum;

    /**
     * 质检结果
     */
    @ExcelProperty(value = "质检结果")
    private String pidResult;

    /**
     * 合格标志
     */
    @ExcelProperty(value = "合格标志", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ices_yn")
    private String pidFlag;

    /**
     * 质检人
     */
    @ExcelProperty(value = "质检人")
    private String pidMan;

    /**
     * 质检日期
     */
    @ExcelProperty(value = "质检日期")
    private String pidDate;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long pidDelete;


}
