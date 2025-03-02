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
 * @date 2025-02-08
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
     * 产品名称
     */
    @ExcelProperty(value = "产品名称")
    private String prName;

    /**
     * 库存
     */
    @ExcelProperty(value = "库存")
    private Long prStock;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long prDelete;

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
    private String prDesc;



}
