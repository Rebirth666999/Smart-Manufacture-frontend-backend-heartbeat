package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 原料台账视图对象 ices_material_record
 *
 * @author ruoyi
 * @date 2025-02-14
 */
@Data
@ExcelIgnoreUnannotated
public class IcesMaterialRecordVo {

    private static final long serialVersionUID = 1L;

    /**
     * 原料台账ID
     */
    @ExcelProperty(value = "原料台账ID")
    private Long mrId;

    /**
     * 所属原料ID
     */
    @ExcelProperty(value = "所属原料ID")
    private Long maId;

    /**
     * 变动类型
     */
    @ExcelProperty(value = "变动类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ices_record_type")
    private String mrType;

    /**
     * 预计变动值
     */
    @ExcelProperty(value = "预计变动值")
    private Long mrEst;

    /**
     * 实际变动值
     */
    @ExcelProperty(value = "实际变动值")
    private Long mrReal;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long mrDelete;

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


}
