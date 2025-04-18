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
 * 客户基本信息视图对象 ices_client
 *
 * @author ruoyi
 * @date 2025-03-28
 */
@Data
@ExcelIgnoreUnannotated
public class IcesClientVo {

    private static final long serialVersionUID = 1L;

    /**
     * 基本信息ID
     */
    @ExcelProperty(value = "基本信息ID")
    private Long clId;

    /**
     * 客户编码
     */
    @ExcelProperty(value = "客户编码")
    private String clCode;

    /**
     * 客户等级
     */
    @ExcelProperty(value = "客户等级")
    private String cllCode;

    /**
     * 业务员ID
     */
    @ExcelProperty(value = "业务员ID")
    private Long clOperator;

    /**
     * 客户名称(中)
     */
    @ExcelProperty(value = "客户名称(中)")
    private String clName;

    /**
     * 客户名称(英)
     */
    @ExcelProperty(value = "客户名称(英)")
    private String clNameEn;

    /**
     * 法人代表
     */
    @ExcelProperty(value = "法人代表")
    private String clLegalRepres;

    /**
     * 企业联系人
     */
    @ExcelProperty(value = "企业联系人")
    private String clContact;

    /**
     * 联系电话1
     */
    @ExcelProperty(value = "联系电话1")
    private String clPhone1;

    /**
     * 联系电话2
     */
    @ExcelProperty(value = "联系电话2")
    private String clPhone2;

    /**
     * 传真
     */
    @ExcelProperty(value = "传真")
    private String clFax;

    /**
     * 邮政编码
     */
    @ExcelProperty(value = "邮政编码")
    private String clPostcode;

    /**
     * 联系地址
     */
    @ExcelProperty(value = "联系地址")
    private String clAddr;

    /**
     * 企业性质
     */
    @ExcelProperty(value = "企业性质")
    private String clType;

    /**
     * 信用等级
     */
    @ExcelProperty(value = "信用等级")
    private String clCreditRank;

    /**
     * 信用额度
     */
    @ExcelProperty(value = "信用额度")
    private String clCreditQuota;

    /**
     * 注册资金
     */
    @ExcelProperty(value = "注册资金")
    private String clRegisterCaptial;

    /**
     * 注册号码
     */
    @ExcelProperty(value = "注册号码")
    private String clRegisterNo;

    /**
     * 银行卡号
     */
    @ExcelProperty(value = "银行卡号")
    private String clBankNum;

    /**
     * 开户行
     */
    @ExcelProperty(value = "开户行")
    private String clBank;

    /**
     * 网址
     */
    @ExcelProperty(value = "网址")
    private String clWebsite;

    /**
     * 注册日期
     */
    @ExcelProperty(value = "注册日期")
    private Date clRegisterDate;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ices_client_status")
    private String clStat;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long clDelete;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String clDesc;

    /**
     * 创建人
     */
    @ExcelProperty(value = "创建人")
    private String clCman;
    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private String clCdate;
    /**
     * 发布人
     */
    @ExcelProperty(value = "发布人")
    private String clRman;
    /**
     * 发布时间
     */
    @ExcelProperty(value = "发布时间")
    private String clRdate;
    /**
     * 修改人
     */
    @ExcelProperty(value = "修改人")
    private String clMman;
    /**
     * 修改时间
     */
    @ExcelProperty(value = "修改时间")
    private String clMdate;

}
