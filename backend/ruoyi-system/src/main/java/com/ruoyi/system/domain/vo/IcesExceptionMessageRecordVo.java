package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 异常消息记录视图对象 ices_exception_message_record
 *
 * @author ruoyi
 * @date 2025-04-09
 */
@Data
@ExcelIgnoreUnannotated
public class IcesExceptionMessageRecordVo {

    private static final long serialVersionUID = 1L;

    /**
     * 消息记录ID
     */
    @ExcelProperty(value = "消息记录ID")
    private Long exmrId;

    /**
     * 消息记录编码
     */
    @ExcelProperty(value = "消息记录编码")
    private String exmrCode;

    /**
     * 预警配置
     */
    @ExcelProperty(value = "预警配置")
    private String exwCode;

    /**
     * 预警记录
     */
    @ExcelProperty(value = "预警记录")
    private String exwrCode;

    /**
     * 消息接收人
     */
    @ExcelProperty(value = "消息接收人")
    private Long exmrRecv;

    /**
     * 内容描述
     */
    @ExcelProperty(value = "内容描述")
    private String exmrDesc;

    /**
     * 消息发送时间
     */
    @ExcelProperty(value = "消息发送时间")
    private String exmrTime;

    /**
     * 消息发送结果
     */
    @ExcelProperty(value = "消息发送结果", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ices_exception_message_result")
    private String exmrResult;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long exmrDelete;


}
