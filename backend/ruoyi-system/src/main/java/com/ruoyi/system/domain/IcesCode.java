package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 业务编码对象 ices_code
 *
 * @author ruoyi
 * @date 2025-03-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_code")
public class IcesCode extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 编码ID
     */
    @TableId(value = "co_id")
    private Long coId;
    /**
     * 编码类别
     */
    private String coType;
    /**
     * 编码值
     */
    private Long coNum;
    /**
     * 已删除
     */
    private Long coDelete;

}
