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
     * 设备任务参数编码
     */
    @ExcelProperty(value = "设备任务参数编码")
    private String dtpaCode;

    /**
     * 所属生产任务
     */
    @ExcelProperty(value = "所属生产任务")
    private String mtCode;

    /**
     * 对应操作步骤参数
     */
    @ExcelProperty(value = "对应操作步骤参数")
    private String eospaCode;

    /**
     * 所属设备任务
     */
    @ExcelProperty(value = "所属设备任务")
    private String dtCode;

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
