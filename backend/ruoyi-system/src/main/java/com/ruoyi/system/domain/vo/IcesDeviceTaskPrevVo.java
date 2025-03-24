package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 关联-设备任务的前序任务视图对象 ices_device_task_prev
 *
 * @author ruoyi
 * @date 2025-03-24
 */
@Data
@ExcelIgnoreUnannotated
public class IcesDeviceTaskPrevVo {

    private static final long serialVersionUID = 1L;

    /**
     * 前序任务关联ID
     */
    @ExcelProperty(value = "前序任务关联ID")
    private Long dtprId;

    /**
     * 当前设备任务ID
     */
    @ExcelProperty(value = "当前设备任务ID")
    private Long dtIdCur;

    /**
     * 前序设备任务ID
     */
    @ExcelProperty(value = "前序设备任务ID")
    private Long dtIdPrev;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long dtprDelete;


}
