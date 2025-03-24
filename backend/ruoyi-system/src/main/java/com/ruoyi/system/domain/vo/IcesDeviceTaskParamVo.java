package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 设备任务参数视图对象 ices_device_task_param
 *
 * @author ruoyi
 * @date 2025-03-24
 */
@Data
@ExcelIgnoreUnannotated
public class IcesDeviceTaskParamVo {

    private static final long serialVersionUID = 1L;

    /**
     * 设备任务参数ID
     */
    @ExcelProperty(value = "设备任务参数ID")
    private Long dtpaId;

    /**
     * 对应操作步骤参数ID
     */
    @ExcelProperty(value = "对应操作步骤参数ID")
    private Long eospaId;

    /**
     * 所属设备任务ID
     */
    @ExcelProperty(value = "所属设备任务ID")
    private Long dtId;

    /**
     * 参数值
     */
    @ExcelProperty(value = "参数值")
    private String dtpaValue;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long dtpaDelete;


}
