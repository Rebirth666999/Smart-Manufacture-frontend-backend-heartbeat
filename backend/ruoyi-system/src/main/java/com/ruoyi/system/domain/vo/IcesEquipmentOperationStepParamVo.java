package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 设备操作步骤参数视图对象 ices_equipment_operation_step_param
 *
 * @author ruoyi
 * @date 2025-03-03
 */
@Data
@ExcelIgnoreUnannotated
public class IcesEquipmentOperationStepParamVo {

    private static final long serialVersionUID = 1L;

    /**
     * 设备任务参数ID
     */
    @ExcelProperty(value = "设备任务参数ID")
    private Long eospaId;

    /**
     * 操作步骤参数编码
     */
    @ExcelProperty(value = "操作步骤参数编码")
    private String eospaCode;

    /**
     * 所属设备操作
     */
    @ExcelProperty(value = "所属设备操作")
    private String eoCode;

    /**
     * 所属设备操作步骤
     */
    @ExcelProperty(value = "所属设备操作步骤")
    private String eosCode;

    /**
     * 父级参数
     */
    @ExcelProperty(value = "父级参数")
    private String eospaCodeParent;

    /**
     * 参数名称
     */
    @ExcelProperty(value = "参数名称")
    private String eospaName;

    /**
     * 参数类型
     */
    @ExcelProperty(value = "参数类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ices_param_type")
    private String eospaType;

    /**
     * 参数位置
     */
    @ExcelProperty(value = "参数位置", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ices_param_position")
    private String eospaPos;

    /**
     * 默认值
     */
    @ExcelProperty(value = "默认值")
    private String eospaValue;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long eospaDelete;


}
