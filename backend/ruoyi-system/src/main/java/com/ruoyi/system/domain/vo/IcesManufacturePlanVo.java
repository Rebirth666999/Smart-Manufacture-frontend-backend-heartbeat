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
 * 生产计划视图对象 ices_manufacture_plan
 *
 * @author ruoyi
 * @date 2025-03-17
 */
@Data
@ExcelIgnoreUnannotated
public class IcesManufacturePlanVo {

    private static final long serialVersionUID = 1L;

    /**
     * 生产计划ID
     */
    @ExcelProperty(value = "生产计划ID")
    private Long mpId;

    /**
     * 生产计划编码
     */
    @ExcelProperty(value = "生产计划编码")
    private String mpCode;

    /**
     * 所属订单
     */
    @ExcelProperty(value = "所属订单")
    private String orCode;

    /**
     * 采用工艺流程
     */
    @ExcelProperty(value = "采用工艺流程")
    private String procCode;

    /**
     * 状态代码
     */
    @ExcelProperty(value = "状态代码", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ices_manufacture_plan_status")
    private String mpStat;

    /**
     * 实际开始时间
     */
    @ExcelProperty(value = "实际开始时间")
    private Date mpBegin;

    /**
     * 最晚结束时间
     */
    @ExcelProperty(value = "最晚结束时间")
    private Date mpEndPlan;

    /**
     * 实际结束时间
     */
    @ExcelProperty(value = "实际结束时间")
    private Date mpEndReal;

    /**
     * 生产计划优先级
     */
    @ExcelProperty(value = "生产计划优先级")
    private Long mpPriority;

    /**
     * 计划产品数量
     */
    @ExcelProperty(value = "计划产品数量")
    private Float mpQtyPlan;

    /**
     * 已完成产品数量
     */
    @ExcelProperty(value = "已完成产品数量")
    private Float mpQtyReal;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long mpDelete;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String mpDesc;


}
