package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 客户贸易信息视图对象 ices_client_trade
 *
 * @author ruoyi
 * @date 2025-03-28
 */
@Data
@ExcelIgnoreUnannotated
public class IcesClientTradeVo {

    private static final long serialVersionUID = 1L;

    /**
     * 贸易信息ID
     */
    @ExcelProperty(value = "贸易信息ID")
    private Long ctId;

    /**
     * 贸易信息编码
     */
    @ExcelProperty(value = "贸易信息编码")
    private String ctCode;

    /**
     * 客户
     */
    @ExcelProperty(value = "客户")
    private String clCode;

    /**
     * 收货人
     */
    @ExcelProperty(value = "收货人")
    private String ctName;

    /**
     * 收货地址
     */
    @ExcelProperty(value = "收货地址")
    private String ctAddr;

    /**
     * 收货电话
     */
    @ExcelProperty(value = "收货电话")
    private String ctPhone;

    /**
     * 国家
     */
    @ExcelProperty(value = "国家")
    private String ctCountry;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long ctDelete;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String ctDesc;


}
