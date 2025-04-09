package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 异常预警配置视图对象 ices_exception_warning
 *
 * @author ruoyi
 * @date 2025-04-09
 */
@Data
@ExcelIgnoreUnannotated
public class IcesExceptionWarningVo {

    private static final long serialVersionUID = 1L;

    /**
     * 预警配置ID
     */
    @ExcelProperty(value = "预警配置ID")
    private Long exwId;

    /**
     * 预警配置编码
     */
    @ExcelProperty(value = "预警配置编码")
    private String exwCode;

    /**
     * 异常
     */
    @ExcelProperty(value = "异常")
    private String exCode;

    /**
     * 异常源
     */
    @ExcelProperty(value = "异常源")
    private String exsCode;

    /**
     * 时间表达式
     */
    @ExcelProperty(value = "时间表达式")
    private String exwFormula;

    /**
     * 消息接收人
     */
    @ExcelProperty(value = "消息接收人")
    private Long exwRecv;

    /**
     * 消息通道
     */
    @ExcelProperty(value = "消息通道")
    private String exwTunnel;

    /**
     * 消息模板
     */
    @ExcelProperty(value = "消息模板")
    private String exwTemplate;

    /**
     * 是否触发源系统预警
     */
    @ExcelProperty(value = "是否触发源系统预警", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ices_yn")
    private String exwWarningOrgn;

    /**
     * 是否触发源系统处理
     */
    @ExcelProperty(value = "是否触发源系统处理", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ices_yn")
    private String exwHandleOrgn;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long exwDelete;


}
