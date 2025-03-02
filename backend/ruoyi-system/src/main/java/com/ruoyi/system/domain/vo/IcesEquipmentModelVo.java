package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 设备模型视图对象 ices_equipment_model
 *
 * @author ruoyi
 * @date 2025-02-26
 */
@Data
@ExcelIgnoreUnannotated
public class IcesEquipmentModelVo {

    private static final long serialVersionUID = 1L;

    /**
     * 设备模型ID
     */
    @ExcelProperty(value = "设备模型ID")
    private Long emId;

    /**
     * 所属模型类型ID
     */
    @ExcelProperty(value = "所属模型类型ID")
    private Long emtId;

    /**
     * 名称
     */
    @ExcelProperty(value = "名称")
    private String emName;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ices_equipment_model_status")
    private String emStat;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long emDelete;

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

    /**
     * 描述
     */
    @ExcelProperty(value = "更新时间")
    private String emDesc;


}
