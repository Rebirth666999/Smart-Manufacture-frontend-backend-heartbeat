package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 产品台账视图对象 ices_product_ledger
 *
 * @author ruoyi
 * @date 2025-04-18
 */
@Data
@ExcelIgnoreUnannotated
public class IcesProductLedgerVo {

    private static final long serialVersionUID = 1L;

    /**
     * 台账ID
     */
    @ExcelProperty(value = "台账ID")
    private Long plId;

    /**
     * 台账编码
     */
    @ExcelProperty(value = "台账编码")
    private String plCode;

    /**
     * 产品
     */
    @ExcelProperty(value = "产品")
    private String prCode;

    /**
     * 库存量
     */
    @ExcelProperty(value = "库存量")
    private Float plStock;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long plDelete;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String plDesc;


}
