package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 产品台账视图对象 ices_product_record
 *
 * @author ruoyi
 * @date 2025-02-18
 */
@Data
@ExcelIgnoreUnannotated
public class IcesProductRecordVo {

    private static final long serialVersionUID = 1L;

    /**
     * 产品台账ID
     */
    @ExcelProperty(value = "产品台账ID")
    private Long prrId;

    /**
     * 所属产品ID
     */
    @ExcelProperty(value = "所属产品ID")
    private Long prId;

    /**
     * 变动类型
     */
    @ExcelProperty(value = "变动类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ices_record_type")
    private String prrType;

    /**
     * 预计变动值
     */
    @ExcelProperty(value = "预计变动值")
    private Long prrEst;

    /**
     * 实际变动值
     */
    @ExcelProperty(value = "实际变动值")
    private Long prrReal;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long prrDelete;

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
