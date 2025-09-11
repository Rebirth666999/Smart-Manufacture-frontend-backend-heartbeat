package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 异常生命周期视图对象 ices_exception_lifecycle
 *
 * @author ruoyi
 * @date 2025-04-08
 */
@Data
@ExcelIgnoreUnannotated
public class IcesExceptionLifecycleVo {

    private static final long serialVersionUID = 1L;

    /**
     * 生命周期ID
     */
    @ExcelProperty(value = "生命周期ID")
    private Long exlId;

    /**
     * 生命周期编码
     */
    @ExcelProperty(value = "生命周期编码")
    private String exlCode;

    /**
     * 所属异常
     */
    @ExcelProperty(value = "所属异常")
    private String exCode;

    /**
     * 缩略图
     */
    @ExcelProperty(value = "缩略图")
    private String exlSnapshot;

    /**
     * 模型ID
     */
    @ExcelProperty(value = "模型ID")
    private String exlModelId;

    /**
     * 模型key
     */
    @ExcelProperty(value = "模型key")
    private String exlModelKey;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long exlDelete;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String exlDesc;

    /*  异常上报编码 */
    @ExcelProperty(value = "异常上报编码")
    private String exrCode;


}
