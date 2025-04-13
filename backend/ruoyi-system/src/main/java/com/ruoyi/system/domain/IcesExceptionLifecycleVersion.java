package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常生命周期版本对象 ices_exception_lifecycle_version
 *
 * @author ruoyi
 * @date 2025-04-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_exception_lifecycle_version")
public class IcesExceptionLifecycleVersion extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 生命周期版本ID
     */
    @TableId(value = "exlv_id")
    private Long exlvId;
    /**
     * 生命周期版本编码
     */
    private String exlvCode;
    /**
     * 所属生命周期
     */
    private String exlCode;
    /**
     * 版本名称
     */
    private String exlvName;
    /**
     * 模型定义ID
     */
    private String exlvDefId;
    /**
     * 模型文件ID
     */
    private String exlvGeId;
    /**
     * 已删除
     */
    private Long exlvDelete;

}
