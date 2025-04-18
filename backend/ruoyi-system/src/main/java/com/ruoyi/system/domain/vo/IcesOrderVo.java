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
     * 订单编码
     */
    @ExcelProperty(value = "订单编码")
    private String orCode;

    /**
     * 客户
     */
    @ExcelProperty(value = "客户")
    private String clCode;

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
    private Float orPrice;

    /**
     * 收货人
     */
    @ExcelProperty(value = "收货人")
    private String orRecv;

    /**
     * 收货地址
     */
    @ExcelProperty(value = "收货地址")
    private String orAddr;

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

    /**
     * 创建人
     */
    @ExcelProperty(value = "创建人")
    private String orCman;
    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private String orCdate;
    /**
     * 修改人
     */
    @ExcelProperty(value = "修改人")
    private String orMman;
    /**
     * 修改时间
     */
    @ExcelProperty(value = "修改时间")
    private String orMdate;

}
