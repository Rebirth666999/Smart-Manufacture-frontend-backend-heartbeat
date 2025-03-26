package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 关联-工艺流程原料需求视图对象 ices_process_material
 *
 * @author ruoyi
 * @date 2025-03-12
 */
@Data
@ExcelIgnoreUnannotated
public class IcesProcessMaterialVo {

    private static final long serialVersionUID = 1L;

    /**
     * 原料需求ID
     */
    @ExcelProperty(value = "原料需求ID")
    private Long pmId;

    /**
     * 所属工艺流程ID
     */
    @ExcelProperty(value = "所属工艺流程ID")
    private Long procId;

    /**
     * 所用原料ID
     */
    @ExcelProperty(value = "所用原料ID")
    private Long maId;

    /**
     * 所需原料数量
     */
    @ExcelProperty(value = "所需原料数量")
    private Float pmDemand;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long pmDelete;


}
