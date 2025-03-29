package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 客户优惠策略视图对象 ices_client_preferential
 *
 * @author ruoyi
 * @date 2025-03-24
 */
@Data
@ExcelIgnoreUnannotated
public class IcesClientPreferentialVo {

    private static final long serialVersionUID = 1L;

    /**
     * 优惠策略ID
     */
    @ExcelProperty(value = "优惠策略ID")
    private Long cpId;

    /**
     * 优惠策略编码
     */
    @ExcelProperty(value = "优惠策略编码")
    private String cpCode;

    /**
     * 名称
     */
    @ExcelProperty(value = "名称")
    private String cpName;

    /**
     * 类别
     */
    @ExcelProperty(value = "类别")
    private String cpType;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long cpDelete;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String cpDesc;


}
