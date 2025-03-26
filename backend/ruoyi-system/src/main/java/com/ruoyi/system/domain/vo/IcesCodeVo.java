package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 业务编码视图对象 ices_code
 *
 * @author ruoyi
 * @date 2025-03-26
 */
@Data
@ExcelIgnoreUnannotated
public class IcesCodeVo {

    private static final long serialVersionUID = 1L;

    /**
     * 编码ID
     */
    @ExcelProperty(value = "编码ID")
    private Long coId;

    /**
     * 编码类别
     */
    @ExcelProperty(value = "编码类别")
    private String coType;

    /**
     * 编码值
     */
    @ExcelProperty(value = "编码值")
    private Long coNum;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long coDelete;


}
