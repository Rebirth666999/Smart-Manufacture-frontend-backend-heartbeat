package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 客户基本信息业务对象 ices_client
 *
 * @author ruoyi
 * @date 2025-03-28
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesClientBo extends BaseEntity {

    /**
     * 基本信息ID
     */
    @NotNull(message = "基本信息ID不能为空", groups = { EditGroup.class })
    private Long clId;

    /**
     * 客户编码
     */
    private String clCode;

    /**
     * 客户等级
     */
    @NotBlank(message = "客户等级不能为空", groups = { AddGroup.class, EditGroup.class })
    private String cllCode;

    /**
     * 业务员ID
     */
    private Long clOperator;

    /**
     * 客户名称(中)
     */
    @NotBlank(message = "客户名称(中)不能为空", groups = { AddGroup.class, EditGroup.class })
    private String clName;

    /**
     * 客户名称(英)
     */
    private String clNameEn;

    /**
     * 法人代表
     */
    private String clLegalRepres;

    /**
     * 企业联系人
     */
    private String clContact;

    /**
     * 联系电话1
     */
    private String clPhone1;

    /**
     * 联系电话2
     */
    private String clPhone2;

    /**
     * 传真
     */
    private String clFax;

    /**
     * 邮政编码
     */
    private String clPostcode;

    /**
     * 联系地址
     */
    private String clAddr;

    /**
     * 企业性质
     */
    private String clType;

    /**
     * 信用等级
     */
    private String clCreditRank;

    /**
     * 信用额度
     */
    private String clCreditQuota;

    /**
     * 注册资金
     */
    private String clRegisterCaptial;

    /**
     * 注册号码
     */
    private String clRegisterNo;

    /**
     * 银行卡号
     */
    private String clBankNum;

    /**
     * 开户行
     */
    private String clBank;

    /**
     * 网址
     */
    private String clWebsite;

    /**
     * 注册日期
     */
    private Date clRegisterDate;

    /**
     * 状态
     */
    @NotBlank(message = "状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String clStat;

    /**
     * 已删除
     */
    private Long clDelete;

    /**
     * 描述
     */
    private String clDesc;

    /**
     * 创建人
     */
    private String clCman;
    /**
     * 创建时间
     */
    private String clCdate;
    /**
     * 发布人
     */
    private String clRman;
    /**
     * 发布时间
     */
    private String clRdate;
    /**
     * 修改人
     */
    private String clMman;
    /**
     * 修改时间
     */
    private String clMdate;

}
