package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 产品质检单对象 ices_product_inspection
 *
 * @author ruoyi
 * @date 2025-04-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_product_inspection")
public class IcesProductInspection extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 质检单ID
     */
    @TableId(value = "pi_id")
    private Long piId;
    /**
     * 质检单编码
     */
    private String piCode;
    /**
     * 批次
     */
    private String pbCode;
    /**
     * 负责人
     */
    private String piMan;
    /**
     * 开始日期
     */
    private String piSdate;
    /**
     * 部门
     */
    private String piDept;
    /**
     * 结束日期
     */
    private String piEdate;
    /**
     * 已删除
     */
    private Long piDelete;

}
