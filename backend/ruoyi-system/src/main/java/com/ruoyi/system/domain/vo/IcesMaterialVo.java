package com.ruoyi.system.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 原料视图对象 ices_material
 *
 * @author ruoyi
 * @date 2025-02-06
 */
@Data
@ExcelIgnoreUnannotated
public class IcesMaterialVo {

    private static final long serialVersionUID = 1L;

    /**
     * 原料ID
     */
    @ExcelProperty(value = "原料ID")
    private Long maId;

    /**
     * 原料名称
     */
    @ExcelProperty(value = "原料名称")
    private String maName;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date maCreateTime;

    /**
     * 修改时间
     */
    @ExcelProperty(value = "修改时间")
    private Date maUpdateTime;


}
