package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 设备模型操作视图对象 ices_model_operation
 *
 * @author ruoyi
 * @date 2025-02-28
 */
@Data
@ExcelIgnoreUnannotated
public class IcesModelOperationVo {

    private static final long serialVersionUID = 1L;

    /**
     * 模型操作ID
     */
    @ExcelProperty(value = "模型操作ID")
    private Long moId;

    /**
     * 所属设备模型ID
     */
    @ExcelProperty(value = "所属设备模型ID")
    private Long emId;

    /**
     * 名称
     */
    @ExcelProperty(value = "名称")
    private String moName;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long moDelete;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String moDesc;


}
