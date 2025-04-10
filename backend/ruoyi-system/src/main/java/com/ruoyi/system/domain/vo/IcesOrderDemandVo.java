package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 订单所需产品关联视图对象 ices_order_demand
 *
 * @author ruoyi
 * @date 2025-04-10
 */
@Data
@ExcelIgnoreUnannotated
public class IcesOrderDemandVo {

    private static final long serialVersionUID = 1L;

    /**
     * 所需产品ID
     */
    @ExcelProperty(value = "所需产品ID")
    private Long odId;

    /**
     * 所需产品编码
     */
    @ExcelProperty(value = "所需产品编码")
    private String odCode;

    /**
     * 所需产品
     */
    @ExcelProperty(value = "所需产品")
    private String maCode;

    /**
     * 订单
     */
    @ExcelProperty(value = "订单")
    private String orCode;

    /**
     * 所需产品数量
     */
    @ExcelProperty(value = "所需产品数量")
    private Long odDemand;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long odDelete;


}
