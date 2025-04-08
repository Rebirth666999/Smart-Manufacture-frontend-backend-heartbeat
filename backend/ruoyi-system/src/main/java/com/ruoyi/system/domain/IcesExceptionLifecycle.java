package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常生命周期对象 ices_exception_lifecycle
 *
 * @author ruoyi
 * @date 2025-04-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_exception_lifecycle")
public class IcesExceptionLifecycle extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 生命周期ID
     */
    @TableId(value = "exl_id")
    private Long exlId;
    /**
     * 生命周期编码
     */
    private String exlCode;
    /**
     * 所属异常
     */
    private String exCode;
    /**
     * 缩略图
     */
    private String exlSnapshot;
    /**
     * 模型ID
     */
    private String exlModelId;
    /**
     * 模型key
     */
    private String exlModelKey;
    /**
     * 已删除
     */
    private Long exlDelete;
    /**
     * 描述
     */
    private String exlDesc;

}
