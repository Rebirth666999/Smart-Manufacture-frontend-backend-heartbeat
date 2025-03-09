package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 工艺步骤视图对象 ices_process_step
 *
 * @author ruoyi
 * @date 2025-03-09
 */
@Data
@ExcelIgnoreUnannotated
public class IcesProcessStepVo {

    private static final long serialVersionUID = 1L;

    /**
     * 工艺步骤ID
     */
    @ExcelProperty(value = "工艺步骤ID")
    private Long psId;

    /**
     * 所属工艺流程ID
     */
    @ExcelProperty(value = "所属工艺流程ID")
    private Long procId;

    /**
     * 设备模型操作ID
     */
    @ExcelProperty(value = "设备模型操作ID")
    private Long moId;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long psDelete;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间")
    private Date updateTime;


}
