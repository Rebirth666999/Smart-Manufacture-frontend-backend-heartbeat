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
 * @date 2025-04-01
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
     * 台账编码
     */
    @ExcelProperty(value = "台账编码")
    private String mrCode;

    /**
     * 物料
     */
    @ExcelProperty(value = "物料")
    private String maCode;

    /**
     * 仓库
     */
    @ExcelProperty(value = "仓库")
    private String stCode;

    /**
     * 变动类型
     */
    @ExcelProperty(value = "变动类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ices_change_type")
    private String mrType;

    /**
     * 台账状态
     */
    @ExcelProperty(value = "台账状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ices_material_status")
    private String mrStat;

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
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String mrDesc;


}
