package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 仓库视图对象 ices_store
 *
 * @author ruoyi
 * @date 2025-03-26
 */
@Data
@ExcelIgnoreUnannotated
public class IcesStoreVo {

    private static final long serialVersionUID = 1L;

    /**
     * 仓库ID
     */
    @ExcelProperty(value = "仓库ID")
    private Long stId;

    /**
     * 仓库编码
     */
    @ExcelProperty(value = "仓库编码")
    private String stCode;

    /**
     * 仓库名称
     */
    @ExcelProperty(value = "仓库名称")
    private String stName;

    /**
     * 仓库类型
     */
    @ExcelProperty(value = "仓库类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ices_store_type")
    private String stType;

    /**
     * 货位数量
     */
    @ExcelProperty(value = "货位数量")
    private Float stSpace;

    /**
     * 空闲货位数量
     */
    @ExcelProperty(value = "空闲货位数量")
    private Float stFree;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long stDelete;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String stDesc;


}
