package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 设备原子操作视图对象 ices_equipment_atom_operation
 *
 * @author ruoyi
 * @date 2025-03-02
 */
@Data
@ExcelIgnoreUnannotated
public class IcesEquipmentAtomOperationVo {

    private static final long serialVersionUID = 1L;

    /**
     * 设备原子操作ID
     */
    @ExcelProperty(value = "设备原子操作ID")
    private Long eaoId;

    /**
     * 原子操作编码
     */
    @ExcelProperty(value = "原子操作编码")
    private String eaoCode;

    /**
     * 所属设备
     */
    @ExcelProperty(value = "所属设备")
    private String eqCode;

    /**
     * 原子操作名称
     */
    @ExcelProperty(value = "原子操作名称")
    private String eaoName;

    /**
     * 操作类型
     */
    @ExcelProperty(value = "操作类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ices_equipment_atom_operation_type")
    private String eaoType;

    /**
     * 请求方法
     */
    @ExcelProperty(value = "请求方法")
    private String eaoRequestType;

    /**
     * URL
     */
    @ExcelProperty(value = "URL")
    private String eaoUrl;

    /**
     * 执行时长
     */
    @ExcelProperty(value = "执行时长")
    private Float eaoExecTime;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long eaoDelete;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String eaoDesc;


}
