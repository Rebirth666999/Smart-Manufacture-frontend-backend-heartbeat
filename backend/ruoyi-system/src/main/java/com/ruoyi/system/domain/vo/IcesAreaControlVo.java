package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 主控节点视图对象 ices_area_control
 *
 * @author ruoyi
 * @date 2025-02-19
 */
@Data
@ExcelIgnoreUnannotated
public class IcesAreaControlVo {

    private static final long serialVersionUID = 1L;

    /**
     * 主控节点ID
     */
    @ExcelProperty(value = "主控节点ID")
    private Long acId;

    /**
     * 所属车间ID
     */
    @ExcelProperty(value = "所属车间ID")
    private Long arId;

    /**
     * 名称
     */
    @ExcelProperty(value = "名称")
    private String acName;

    /**
     * IP地址
     */
    @ExcelProperty(value = "IP地址")
    private String acIp;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long acDelete;


}
