package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 关联-车间原料库存视图对象 ices_material_stock
 *
 * @author ruoyi
 * @date 2025-04-01
 */
@Data
@ExcelIgnoreUnannotated
public class IcesMaterialStockVo {

    private static final long serialVersionUID = 1L;

    /**
     * 车间原料库存ID
     */
    @ExcelProperty(value = "车间原料库存ID")
    private Long msId;

    /**
     * 仓库原料库存编码
     */
    @ExcelProperty(value = "仓库原料库存编码")
    private String msCode;

    /**
     * 仓库
     */
    @ExcelProperty(value = "仓库")
    private String stCode;

    /**
     * 物料
     */
    @ExcelProperty(value = "物料")
    private String maCode;

    /**
     * 库存
     */
    @ExcelProperty(value = "库存")
    private Long msStock;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long msDelete;


}
