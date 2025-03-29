package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 关联-客户等级对应的优惠策略视图对象 ices_client_level_preferential
 *
 * @author ruoyi
 * @date 2025-03-27
 */
@Data
@ExcelIgnoreUnannotated
public class IcesClientLevelPreferentialVo {

    private static final long serialVersionUID = 1L;

    /**
     * 对应关系ID
     */
    @ExcelProperty(value = "对应关系ID")
    private Long clpId;

    /**
     * 对应关系编码
     */
    @ExcelProperty(value = "对应关系编码")
    private String clpCode;

    /**
     * 客户等级
     */
    @ExcelProperty(value = "客户等级")
    private String cllCode;

    /**
     * 优惠策略
     */
    @ExcelProperty(value = "优惠策略")
    private String cpCode;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long clpDelete;


}
