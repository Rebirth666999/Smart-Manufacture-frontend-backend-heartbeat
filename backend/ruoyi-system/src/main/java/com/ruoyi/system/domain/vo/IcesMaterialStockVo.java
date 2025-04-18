package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 仓库原料库存视图对象 ices_material_stock
 *
 * @author ruoyi
 * @date 2025-04-18
 */
@Data
@ExcelIgnoreUnannotated
public class IcesMaterialStockVo {

    private static final long serialVersionUID = 1L;

    /**
     * 仓库原料库存ID
     */
    @ExcelProperty(value = "仓库原料库存ID")
    private Long mssId;

    /**
     * 仓库原料库存编码
     */
    @ExcelProperty(value = "仓库原料库存编码")
    private String mssCode;

    /**
     * 仓库
     */
    @ExcelProperty(value = "仓库")
    private String msCode;

    /**
     * 物料
     */
    @ExcelProperty(value = "物料")
    private String maCode;

    /**
     * 库存
     */
    @ExcelProperty(value = "库存")
    private Float mssStock;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long mssDelete;


}
