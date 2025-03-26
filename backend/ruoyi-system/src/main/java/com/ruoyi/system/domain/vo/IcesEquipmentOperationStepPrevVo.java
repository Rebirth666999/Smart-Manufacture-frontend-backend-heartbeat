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
     * 所属设备操作
     */
    @ExcelProperty(value = "所属设备操作")
    private String eoCode;

    /**
     * 前序步骤关联编码
     */
    @ExcelProperty(value = "前序步骤关联编码")
    private String eosprCode;

    /**
     * 当前设备操作步骤
     */
    @ExcelProperty(value = "当前设备操作步骤")
    private String eosCodeCur;

    /**
     * 前序设备操作步骤
     */
    @ExcelProperty(value = "前序设备操作步骤")
    private String eosCodePrev;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long eosprDelete;


}
