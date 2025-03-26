package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 客户等级视图对象 ices_client_level
 *
 * @author ruoyi
 * @date 2025-03-24
 */
@Data
@ExcelIgnoreUnannotated
public class IcesClientLevelVo {

    private static final long serialVersionUID = 1L;

    /**
     * 客户等级ID
     */
    @ExcelProperty(value = "客户等级ID")
    private Long cllId;

    /**
     * 客户等级编码
     */
    @ExcelProperty(value = "客户等级编码")
    private String cllCode;

    /**
     * 客户等级标识
     */
    @ExcelProperty(value = "客户等级标识")
    private String cllLabel;

    /**
     * 客户等级名称
     */
    @ExcelProperty(value = "客户等级名称")
    private String cllName;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long cllDelete;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String cllDesc;


}
