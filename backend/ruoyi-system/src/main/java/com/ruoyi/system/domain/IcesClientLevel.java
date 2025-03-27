package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 客户等级对象 ices_client_level
 *
 * @author ruoyi
 * @date 2025-03-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_client_level")
public class IcesClientLevel extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 客户等级ID
     */
    @TableId(value = "cll_id")
    private Long cllId;
    /**
     * 客户等级编码
     */
    private String cllCode;
    /**
     * 客户等级标识
     */
    private String cllLabel;
    /**
     * 客户等级名称
     */
    private String cllName;
    /**
     * 已删除
     */
    private Long cllDelete;
    /**
     * 描述
     */
    private String cllDesc;

}
