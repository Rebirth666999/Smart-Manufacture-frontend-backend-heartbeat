package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常源对象 ices_exception_source
 *
 * @author ruoyi
 * @date 2025-04-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ices_exception_source")
public class IcesExceptionSource extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 异常源ID
     */
    @TableId(value = "exs_id")
    private Long exsId;
    /**
     * 异常源编码
     */
    private String exsCode;
    /**
     * 名称
     */
    private String exsName;
    /**
     * 认证token
     */
    private String exsToken;
    /**
     * 巡检接口
     */
    private String exsInspectionUrl;
    /**
     * 处理接口
     */
    private String exsHandleUrl;
    /**
     * 状态检查接口
     */
    private String exsStateUrl;
    /**
     * 预警接口
     */
    private String exsNotifyUrl;
    /**
     * 已删除
     */
    private Long exsDelete;
    /**
     * 描述
     */
    private String exsDesc;

}
