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
 * 生产任务视图对象 ices_manufacture_task
 *
 * @author ruoyi
 * @date 2025-03-17
 */
@Data
@ExcelIgnoreUnannotated
public class IcesManufactureTaskVo {

    private static final long serialVersionUID = 1L;

    /**
     * 生产任务ID
     */
    @ExcelProperty(value = "生产任务ID")
    private Long mtId;

    /**
     * 生产任务编码
     */
    @ExcelProperty(value = "生产任务编码")
    private String mtCode;

    /**
     * 所属生产计划
     */
    @ExcelProperty(value = "所属生产计划")
    private String mpCode;

    /**
     * 工艺流程
     */
    @ExcelProperty(value = "工艺流程")
    private String procCode;

    /**
     * 目标车间
     */
    @ExcelProperty(value = "目标车间")
    private String arCode;

    /**
     * 原料仓库
     */
    @ExcelProperty(value = "原料仓库")
    private String msCode;

    /**
     * 产品仓库
     */
    @ExcelProperty(value = "产品仓库")
    private String prsCode;

    /**
     * 状态代码
     */
    @ExcelProperty(value = "状态代码", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ices_order_status")
    private String mtStat;

    /**
     * 实际开始时间
     */
    @ExcelProperty(value = "实际开始时间")
    private Date mtBegin;

    /**
     * 最晚结束时间
     */
    @ExcelProperty(value = "最晚结束时间")
    private Date mtEndPlan;

    /**
     * 实际结束时间
     */
    @ExcelProperty(value = "实际结束时间")
    private Date mtEndReal;

    /**
     * 任务优先级
     */
    @ExcelProperty(value = "任务优先级")
    private Long mtPriority;

    /**
     * 计划产品数量
     */
    @ExcelProperty(value = "计划产品数量")
    private Float mtQtyPlan;

    /**
     * 已完成产品数量
     */
    @ExcelProperty(value = "已完成产品数量")
    private Float mtQtyReal;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long mtDelete;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String mtDesc;


    /**
     * 创建人
     */
    @ExcelProperty(value = "创建人")
    private String mtCman;
    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private String mtCdate;
    /**
     * 发布人
     */
    @ExcelProperty(value = "发布人")
    private String mtRman;
    /**
     * 发布时间
     */
    @ExcelProperty(value = "发布时间")
    private String mtRdate;
    /**
     * 修改人
     */
    @ExcelProperty(value = "修改人")
    private String mtMman;
    /**
     * 修改时间
     */
    @ExcelProperty(value = "修改时间")
    private String mtMdate;
}
