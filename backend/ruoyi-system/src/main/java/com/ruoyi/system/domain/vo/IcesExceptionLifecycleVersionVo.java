package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 异常生命周期版本视图对象 ices_exception_lifecycle_version
 *
 * @author ruoyi
 * @date 2025-04-08
 */
@Data
@ExcelIgnoreUnannotated
public class IcesExceptionLifecycleVersionVo {

    private static final long serialVersionUID = 1L;

    /**
     * 生命周期版本ID
     */
    @ExcelProperty(value = "生命周期版本ID")
    private Long exlvId;

    /**
     * 生命周期版本编码
     */
    @ExcelProperty(value = "生命周期版本编码")
    private String exlvCode;

    /**
     * 所属生命周期
     */
    @ExcelProperty(value = "所属生命周期")
    private String exlCode;

    /**
     * 版本名称
     */
    @ExcelProperty(value = "版本名称")
    private String exlvName;

    /**
     * 模型定义ID
     */
    @ExcelProperty(value = "模型定义ID")
    private String exlvDefId;

    /**
     * 模型文件ID
     */
    @ExcelProperty(value = "模型文件ID")
    private String exlvGeId;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long exlvDelete;


}
