package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 产品视图对象 ices_product
 *
 * @author ruoyi
 * @date 2025-04-17
 */
@Data
@ExcelIgnoreUnannotated
public class IcesProductVo {

    private static final long serialVersionUID = 1L;

    /**
     * 产品ID
     */
    @ExcelProperty(value = "产品ID")
    private Long prId;

    /**
     * 产品编码
     */
    @ExcelProperty(value = "产品编码")
    private String prCode;

    /**
     * 产品名称
     */
    @ExcelProperty(value = "产品名称")
    private String prName;

    /**
     * 占用货位数量
     */
    @ExcelProperty(value = "占用货位数量")
    private Long prOccupy;

    /**
     * 定制详情
     */
    @ExcelProperty(value = "定制详情")
    private String prCust;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long prDelete;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String prDesc;


}
