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
 * 生产计划(主)视图对象 ices_manufacture_plan_main
 *
 * @author ruoyi
 * @date 2025-04-25
 */
@Data
@ExcelIgnoreUnannotated
public class IcesManufacturePlanMainVo {

    private static final long serialVersionUID = 1L;

    /**
     * 生产计划ID
     */
    @ExcelProperty(value = "生产计划ID")
    private Long mpmId;

    /**
     * 生产计划编码
     */
    @ExcelProperty(value = "生产计划编码")
    private String mpmCode;

    /**
     * 所属订单
     */
    @ExcelProperty(value = "所属订单")
    private String orCode;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态")
    private String mpmStat;

    /**
     * 实际开始时间
     */
    @ExcelProperty(value = "实际开始时间")
    private Date mpmBegin;

    /**
     * 最晚结束时间
     */
    @ExcelProperty(value = "最晚结束时间")
    private Date mpmEndPlan;

    /**
     * 实际结束时间
     */
    @ExcelProperty(value = "实际结束时间")
    private Date mpmEndReal;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long mpmDelete;

    /**
     * 创建人
     */
    @ExcelProperty(value = "创建人")
    private String mpmCman;

    /**
     * 创建日期
     */
    @ExcelProperty(value = "创建日期")
    private String mpmCdate;

    /**
     * 修改人
     */
    @ExcelProperty(value = "修改人")
    private String mpmMman;

    /**
     * 修改日期
     */
    @ExcelProperty(value = "修改日期")
    private String mpmMdate;


}
