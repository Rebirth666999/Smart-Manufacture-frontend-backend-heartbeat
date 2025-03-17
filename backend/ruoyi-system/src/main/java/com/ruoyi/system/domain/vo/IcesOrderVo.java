package com.ruoyi.system.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 订单视图对象 ices_order
 *
 * @author ruoyi
 * @date 2025-03-17
 */
@Data
@ExcelIgnoreUnannotated
public class IcesOrderVo {

    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    @ExcelProperty(value = "订单ID")
    private Long orId;

    /**
     * 所需产品ID
     */
    @ExcelProperty(value = "所需产品ID")
    private Long prId;

    /**
     * 客户ID
     */
    @ExcelProperty(value = "客户ID")
    private Long clId;

    /**
     * 订单名称
     */
    @ExcelProperty(value = "订单名称")
    private String orName;

    /**
     * 状态代码
     */
    @ExcelProperty(value = "状态代码", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ices_order_status")
    private String orStat;

    /**
     * 所需产品数量
     */
    @ExcelProperty(value = "所需产品数量")
    private Long orDemand;

    /**
     * 订单优先级
     */
    @ExcelProperty(value = "订单优先级")
    private Long orPriority;

    /**
     * 截止时间
     */
    @ExcelProperty(value = "截止时间")
    private Date orDeadline;

    /**
     * 总价
     */
    @ExcelProperty(value = "总价")
    private Long orPrice;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long orDelete;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String orDesc;


}
