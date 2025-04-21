package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 产品质检单明细对象 ices_product_inspection_detail
 *
 * @author ruoyi
 * @date 2025-04-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_product_inspection_detail")
public class IcesProductInspectionDetail extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 质检单明细ID
     */
    @TableId(value = "pid_id")
    private Long pidId;
    /**
     * 质检单明细编码
     */
    private String pidCode;
    /**
     * 所属质检单
     */
    private String piCode;
    /**
     * 产品类型
     */
    private String prCode;
    /**
     * 产品编码
     */
    private String pidBatchNum;
    /**
     * 质检结果
     */
    private String pidResult;
    /**
     * 合格标志
     */
    private String pidFlag;
    /**
     * 质检人
     */
    private String pidMan;
    /**
     * 质检日期
     */
    private String pidDate;
    /**
     * 已删除
     */
    private Long pidDelete;

}
