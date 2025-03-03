package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 设备操作步骤视图对象 ices_equipment_operation_step
 *
 * @author ruoyi
 * @date 2025-03-03
 */
@Data
@ExcelIgnoreUnannotated
public class IcesEquipmentOperationStepVo {

    private static final long serialVersionUID = 1L;

    /**
     * 设备操作步骤ID
     */
    @ExcelProperty(value = "设备操作步骤ID")
    private Long eosId;

    /**
     * 对应原子操作ID
     */
    @ExcelProperty(value = "对应原子操作ID")
    private Long eaoId;

    /**
     * 所属设备操作ID
     */
    @ExcelProperty(value = "所属设备操作ID")
    private Long eoId;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long eosDelete;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String eosDesc;


}
