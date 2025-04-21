package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 实际产品批次编码视图对象 ices_product_batch
 *
 * @author ruoyi
 * @date 2025-04-19
 */
@Data
@ExcelIgnoreUnannotated
public class IcesProductBatchVo {

    private static final long serialVersionUID = 1L;

    /**
     * 产品批次ID
     */
    @ExcelProperty(value = "产品批次ID")
    private Long pbId;

    /**
     * 产品批次编码
     */
    @ExcelProperty(value = "产品批次编码")
    private String pbCode;

    /**
     * 订单产品需求
     */
    @ExcelProperty(value = "订单产品需求")
    private String odCode;

    /**
     * 批次编号
     */
    @ExcelProperty(value = "批次编号")
    private String pbBatch;

    /**
     * 起始号码
     */
    @ExcelProperty(value = "起始号码")
    private Long pbStart;

    /**
     * 终止号码
     */
    @ExcelProperty(value = "终止号码")
    private Long pbEnd;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long pbDelete;


}
