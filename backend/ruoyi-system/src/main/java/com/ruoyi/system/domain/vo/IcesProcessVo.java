package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 工艺流程视图对象 ices_process
 *
 * @author ruoyi
 * @date 2025-03-10
 */
@Data
@ExcelIgnoreUnannotated
public class IcesProcessVo {

    private static final long serialVersionUID = 1L;

    /**
     * 工艺流程ID
     */
    @ExcelProperty(value = "工艺流程ID")
    private Long procId;

    /**
     * 工艺流程编码
     */
    @ExcelProperty(value = "工艺流程编码")
    private String procCode;

    /**
     * 目标产品
     */
    @ExcelProperty(value = "目标产品")
    private String prCode;

    /**
     * 工艺流程名称
     */
    @ExcelProperty(value = "工艺流程名称")
    private String procName;

    /**
     * 自动生成模型ID
     */
    @ExcelProperty(value = "flowable模型ID")
    private String procModel;

    /**
     * 工艺流程状态
     */
    @ExcelProperty(value = "工艺流程状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ices_process_status")
    private String procStat;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long procDelete;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String procDesc;


}
