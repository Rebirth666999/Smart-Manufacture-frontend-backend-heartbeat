package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 原料台账视图对象 ices_material_ledger
 *
 * @author ruoyi
 * @date 2025-04-18
 */
@Data
@ExcelIgnoreUnannotated
public class IcesMaterialLedgerVo {

    private static final long serialVersionUID = 1L;

    /**
     * 台账ID
     */
    @ExcelProperty(value = "台账ID")
    private Long mlId;

    /**
     * 台账编码
     */
    @ExcelProperty(value = "台账编码")
    private String mlCode;

    /**
     * 原料
     */
    @ExcelProperty(value = "原料")
    private String maCode;

    /**
     * 库存量
     */
    @ExcelProperty(value = "库存量")
    private Float mlStock;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long mlDelete;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String mlDesc;


}
