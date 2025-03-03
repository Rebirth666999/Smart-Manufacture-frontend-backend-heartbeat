package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 关联-设备操作步骤的前序步骤视图对象 ices_equipment_operation_step_prev
 *
 * @author ruoyi
 * @date 2025-03-03
 */
@Data
@ExcelIgnoreUnannotated
public class IcesEquipmentOperationStepPrevVo {

    private static final long serialVersionUID = 1L;

    /**
     * 前序步骤关联ID
     */
    @ExcelProperty(value = "前序步骤关联ID")
    private Long eosprId;

    /**
     * 当前设备操作步骤ID
     */
    @ExcelProperty(value = "当前设备操作步骤ID")
    private Long eosIdCur;

    /**
     * 前序设备操作步骤ID
     */
    @ExcelProperty(value = "前序设备操作步骤ID")
    private Long eosIdPrev;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long eosprDelete;


}
