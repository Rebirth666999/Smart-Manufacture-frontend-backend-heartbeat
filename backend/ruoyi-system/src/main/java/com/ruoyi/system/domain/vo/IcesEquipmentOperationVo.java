package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 设备操作视图对象 ices_equipment_operation
 *
 * @author ruoyi
 * @date 2025-03-02
 */
@Data
@ExcelIgnoreUnannotated
public class IcesEquipmentOperationVo {

    private static final long serialVersionUID = 1L;

    /**
     * 设备操作ID
     */
    @ExcelProperty(value = "设备操作ID")
    private Long eoId;

    /**
     * 设备操作编码
     */
    @ExcelProperty(value = "设备操作编码")
    private String eoCode;

    /**
     * 对应模型操作
     */
    @ExcelProperty(value = "对应模型操作")
    private String moCode;

    /**
     * 所属设备
     */
    @ExcelProperty(value = "所属设备")
    private String eqCode;


    /**
     * 后端flowable模型ID
     */
    @ExcelProperty(value = "flowable模型ID")
    private String eoModel;

    /**
     * 名称
     */
    @ExcelProperty(value = "名称")
    private String eoName;

    /**
     * 执行时长
     */
    @ExcelProperty(value = "执行时长")
    private Float eoExecTime;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long eoDelete;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String eoDesc;


}
