package com.ruoyi.system.domain.vo;

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
 * @date 2025-02-07
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
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long maDelete;

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
    private String maDesc;


}
