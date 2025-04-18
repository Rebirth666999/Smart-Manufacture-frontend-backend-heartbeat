package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 产品仓库对象 ices_product_store
 *
 * @author ruoyi
 * @date 2025-04-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_product_store")
public class IcesProductStore extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 产品仓库ID
     */
    @TableId(value = "prs_id")
    private Long prsId;
    /**
     * 产品仓库编码
     */
    private String prsCode;
    /**
     * 产品仓库名称
     */
    private String prsName;
    /**
     * 货位数量
     */
    private Float prsSpace;
    /**
     * 空闲货位数量
     */
    private Float prsFree;
    /**
     * 已删除
     */
    private Long prsDelete;
    /**
     * 描述
     */
    private String prsDesc;

}
