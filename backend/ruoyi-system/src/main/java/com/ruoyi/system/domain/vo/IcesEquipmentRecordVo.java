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
 * 设备事件日志视图对象 ices_equipment_record
 *
 * @author ruoyi
 * @date 2025-03-01
 */
@Data
@ExcelIgnoreUnannotated
public class IcesEquipmentRecordVo {

    private static final long serialVersionUID = 1L;

    /**
     * 事件日志ID
     */
    @ExcelProperty(value = "事件日志ID")
    private Long erId;

    /**
     * 关联设备ID
     */
    @ExcelProperty(value = "关联设备ID")
    private Long eqId;

    /**
     * 事件类型代码
     */
    @ExcelProperty(value = "事件类型代码", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ices_equipment_record_type")
    private String erType;

    /**
     * 事件状态代码
     */
    @ExcelProperty(value = "事件状态代码", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ices_equipment_record_status")
    private String erStat;

    /**
     * 事件等级
     */
    @ExcelProperty(value = "事件等级")
    private Long erLevel;

    /**
     * 发生时间
     */
    @ExcelProperty(value = "发生时间")
    private Date erBegin;

    /**
     * 结束时间
     */
    @ExcelProperty(value = "结束时间")
    private Date erEnd;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long erDelete;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String erDesc;


}
