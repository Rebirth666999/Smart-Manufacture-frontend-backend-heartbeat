package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 车间视图对象 ices_area
 *
 * @author ruoyi
 * @date 2025-02-09
 */
@Data
@ExcelIgnoreUnannotated
public class IcesAreaVo {

    private static final long serialVersionUID = 1L;

    /**
     * 车间ID
     */
    @ExcelProperty(value = "车间ID")
    private Long arId;

    /**
     * 车间名称
     */
    @ExcelProperty(value = "车间名称")
    private String arName;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long arDelete;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间")
    private Date updateTime;


}
