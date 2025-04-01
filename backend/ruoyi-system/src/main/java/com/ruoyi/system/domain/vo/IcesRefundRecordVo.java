package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 退货记录视图对象 ices_refund_record
 *
 * @author ruoyi
 * @date 2025-03-28
 */
@Data
@ExcelIgnoreUnannotated
public class IcesRefundRecordVo {

    private static final long serialVersionUID = 1L;

    /**
     * 退货记录ID
     */
    @ExcelProperty(value = "退货记录ID")
    private Long rrId;

    /**
     * 退货记录编码
     */
    @ExcelProperty(value = "退货记录编码")
    private String rrCode;

    /**
     * 订单
     */
    @ExcelProperty(value = "订单")
    private String orCode;

    /**
     * 产品
     */
    @ExcelProperty(value = "产品")
    private String maCode;

    /**
     * 客户
     */
    @ExcelProperty(value = "客户")
    private String clCode;

    /**
     * 数量
     */
    @ExcelProperty(value = "数量")
    private Long rrCount;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long rrDelete;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String rrDesc;


}
