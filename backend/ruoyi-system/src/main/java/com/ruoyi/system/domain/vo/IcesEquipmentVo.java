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
 * 设备视图对象 ices_equipment
 *
 * @author ruoyi
 * @date 2025-02-28
 */
@Data
@ExcelIgnoreUnannotated
public class IcesEquipmentVo {

    private static final long serialVersionUID = 1L;

    /**
     * 设备ID
     */
    @ExcelProperty(value = "设备ID")
    private Long eqId;

    /**
     * 设备编码
     */
    @ExcelProperty(value = "设备编码")
    private String eqCode;

    /**
     * 所属车间
     */
    @ExcelProperty(value = "所属车间")
    private String arCode;

    /**
     * 所属设备模型
     */
    @ExcelProperty(value = "所属设备模型")
    private String emCode;

    /**
     * 名称
     */
    @ExcelProperty(value = "名称")
    private String eqName;

    /**
     * 状态代码
     */
    @ExcelProperty(value = "状态代码", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ices_equipment_status")
    private String eqStat;

    /**
     * 采购时间
     */
    @ExcelProperty(value = "采购时间")
    private Date eqIntroduceTime;

    /**
     * 上次通讯时间
     */
    @ExcelProperty(value = "上次通讯时间")
    private Date eqCommunicateTime;

    /**
     * IP地址
     */
    @ExcelProperty(value = "IP地址")
    private String eqIp;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long eqDelete;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String eqDesc;

    /**
     * 创建人
     */
    @ExcelProperty(value = "创建人")
    private String eqCman;
    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private String eqCdate;
    /**
     * 发布人
     */
    @ExcelProperty(value = "发布人")
    private String eqRman;
    /**
     * 发布时间
     */
    @ExcelProperty(value = "发布时间")
    private String eqRdate;
    /**
     * 修改人
     */
    @ExcelProperty(value = "修改人")
    private String eqMman;
    /**
     * 修改时间
     */
    @ExcelProperty(value = "修改时间")
    private String eqMdate;

}
