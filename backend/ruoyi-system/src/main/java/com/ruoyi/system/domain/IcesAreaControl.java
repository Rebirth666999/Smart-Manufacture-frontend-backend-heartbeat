package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 主控节点对象 ices_area_control
 *
 * @author ruoyi
 * @date 2025-02-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_area_control")
public class IcesAreaControl extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主控节点ID
     */
    @TableId(value = "ac_id")
    private Long acId;
    /**
     * 所属车间ID
     */
    private Long arId;
    /**
     * 名称
     */
    private String acName;
    /**
     * IP地址
     */
    private String acIp;
    /**
     * 已删除
     */
    private Long acDelete;
    /**
     * 描述
     */
    private String acDesc;

}
