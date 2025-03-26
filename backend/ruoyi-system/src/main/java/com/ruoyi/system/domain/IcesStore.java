package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 仓库对象 ices_store
 *
 * @author ruoyi
 * @date 2025-03-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_store")
public class IcesStore extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 仓库ID
     */
    @TableId(value = "st_id")
    private Long stId;
    /**
     * 仓库编码
     */
    private String stCode;
    /**
     * 仓库名称
     */
    private String stName;
    /**
     * 仓库类型
     */
    private String stType;
    /**
     * 货位数量
     */
    private Float stSpace;
    /**
     * 空闲货位数量
     */
    private Float stFree;
    /**
     * 已删除
     */
    private Long stDelete;
    /**
     * 描述
     */
    private String stDesc;

}
