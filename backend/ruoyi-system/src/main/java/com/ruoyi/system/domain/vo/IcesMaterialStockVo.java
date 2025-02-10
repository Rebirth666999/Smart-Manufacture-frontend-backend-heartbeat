package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 车间原料库存视图对象 ices_material_stock
 *
 * @author ruoyi
 * @date 2025-02-10
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
     * 车间ID
     */
    @ExcelProperty(value = "车间ID")
    private Long arId;

    /**
     * 原料ID
     */
    @ExcelProperty(value = "原料ID")
    private Long maId;

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
