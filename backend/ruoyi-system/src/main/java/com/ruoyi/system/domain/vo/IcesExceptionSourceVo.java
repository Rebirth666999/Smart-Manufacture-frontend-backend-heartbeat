package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 异常源视图对象 ices_exception_source
 *
 * @author ruoyi
 * @date 2025-04-07
 */
@Data
@ExcelIgnoreUnannotated
public class IcesExceptionSourceVo {

    private static final long serialVersionUID = 1L;

    /**
     * 异常源ID
     */
    @ExcelProperty(value = "异常源ID")
    private Long exsId;

    /**
     * 异常源编码
     */
    @ExcelProperty(value = "异常源编码")
    private String exsCode;

    /**
     * 名称
     */
    @ExcelProperty(value = "名称")
    private String exsName;

    /**
     * 认证token
     */
    @ExcelProperty(value = "认证token")
    private String exsToken;

    /**
     * 巡检接口
     */
    @ExcelProperty(value = "巡检接口")
    private String exsInspectionUrl;

    /**
     * 处理接口
     */
    @ExcelProperty(value = "处理接口")
    private String exsHandleUrl;

    /**
     * 状态检查接口
     */
    @ExcelProperty(value = "状态检查接口")
    private String exsStateUrl;

    /**
     * 预警接口
     */
    @ExcelProperty(value = "预警接口")
    private String exsNotifyUrl;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long exsDelete;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String exsDesc;


}
