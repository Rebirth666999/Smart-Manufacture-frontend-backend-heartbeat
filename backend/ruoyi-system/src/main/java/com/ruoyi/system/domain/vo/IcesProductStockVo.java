package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 仓库产品库存视图对象 ices_product_stock
 *
 * @author ruoyi
 * @date 2025-04-18
 */
@Data
@ExcelIgnoreUnannotated
public class IcesProductStockVo {

    private static final long serialVersionUID = 1L;

    /**
     * 仓库产品库存ID
     */
    @ExcelProperty(value = "仓库产品库存ID")
    private Long pssId;

    /**
     * 仓库产品库存编码
     */
    @ExcelProperty(value = "仓库产品库存编码")
    private String pssCode;

    /**
     * 仓库
     */
    @ExcelProperty(value = "仓库")
    private String prsCode;

    /**
     * 产品
     */
    @ExcelProperty(value = "产品")
    private String prCode;

    /**
     * 库存量
     */
    @ExcelProperty(value = "库存量")
    private Float pssStock;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long pssDelete;


}
