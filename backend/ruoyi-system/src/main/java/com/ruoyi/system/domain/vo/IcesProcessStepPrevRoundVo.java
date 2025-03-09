package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;



/**
 * 关联-工艺步骤的跨轮次前序步骤视图对象 ices_process_step_prev_round
 *
 * @author ruoyi
 * @date 2025-03-09
 */
@Data
@ExcelIgnoreUnannotated
public class IcesProcessStepPrevRoundVo {

    private static final long serialVersionUID = 1L;

    /**
     * 跨轮次前序步骤关联ID
     */
    @ExcelProperty(value = "跨轮次前序步骤关联ID")
    private Long psprId;

    /**
     * 当前工艺步骤ID
     */
    @ExcelProperty(value = "当前工艺步骤ID")
    private Long psIdCur;

    /**
     * 跨轮次前序工艺步骤ID
     */
    @ExcelProperty(value = "跨轮次前序工艺步骤ID")
    private Long psIdPrev;

    /**
     * 已删除
     */
    @ExcelProperty(value = "已删除")
    private Long psprDelete;

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
