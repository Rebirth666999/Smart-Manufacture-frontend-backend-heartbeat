package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 产品仓库视图对象 ices_product_store
 *
 * @author ruoyi
 * @date 2025-04-17
 */
@Data
@ExcelIgnoreUnannotated
public class IcesProductStoreVo {

    private static final long serialVersionUID = 1L;

    /**
     * 产品仓库ID
     */
    @ExcelProperty(value = "产品仓库ID")
    private Long prsId;

    /**
     * 产品仓库编码
     */
    @ExcelProperty(value = "产品仓库编码")
    private String prsCode;

    /**
     * 产品仓库名称
     */
    @ExcelProperty(value = "产品仓库名称")
    private String prsName;

    /**
     * 货位数量
     */
    @ExcelProperty(value = "货位数量")
    private Float prsSpace;

    /**
     * 空闲货位数量
     */
    @ExcelProperty(value = "空闲货位数量")
    private Float prsFree;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long prsDelete;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String prsDesc;


}
