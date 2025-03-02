package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 设备模型类型视图对象 ices_equipment_model_type
 *
 * @author ruoyi
 * @date 2025-02-18
 */
@Data
@ExcelIgnoreUnannotated
public class IcesEquipmentModelTypeVo {

    private static final long serialVersionUID = 1L;

    /**
     * 模型类型ID
     */
    @ExcelProperty(value = "模型类型ID")
    private Long emtId;

    /**
     * 名称
     */
    @ExcelProperty(value = "名称")
    private String emtName;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long emtDelete;

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
    @ExcelProperty(value = "描述")
    private String emtDesc;


}
