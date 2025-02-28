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
     * 所属车间ID
     */
    @ExcelProperty(value = "所属车间ID")
    private Long arId;

    /**
     * 所属设备模型ID
     */
    @ExcelProperty(value = "所属设备模型ID")
    private Long emId;

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


}
