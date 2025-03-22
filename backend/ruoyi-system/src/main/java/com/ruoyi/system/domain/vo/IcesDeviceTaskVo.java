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
 * 设备任务视图对象 ices_device_task
 *
 * @author ruoyi
 * @date 2025-03-19
 */
@Data
@ExcelIgnoreUnannotated
public class IcesDeviceTaskVo {

    private static final long serialVersionUID = 1L;

    /**
     * 设备任务ID
     */
    @ExcelProperty(value = "设备任务ID")
    private Long dtId;

    /**
     * 设备ID
     */
    @ExcelProperty(value = "设备ID")
    private Long eqId;

    /**
     * 所属生产任务ID
     */
    @ExcelProperty(value = "所属生产任务ID")
    private Long mtId;

    /**
     * 设备操作ID
     */
    @ExcelProperty(value = "设备操作ID")
    private Long eoId;

    /**
     * 状态代码
     */
    @ExcelProperty(value = "状态代码", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ices_manufacture_task_status")
    private String dtStat;

    /**
     * 实际开始时间
     */
    @ExcelProperty(value = "实际开始时间")
    private Date dtBegin;

    /**
     * 实际结束时间
     */
    @ExcelProperty(value = "实际结束时间")
    private Date dtEnd;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long dtDelete;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String dtDesc;


}
