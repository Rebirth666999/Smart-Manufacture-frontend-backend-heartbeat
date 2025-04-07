package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常业务对象 ices_exception
 *
 * @author ruoyi
 * @date 2025-04-07
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesExceptionBo extends BaseEntity {

    /**
     * 异常ID
     */
    @NotNull(message = "异常ID不能为空", groups = { EditGroup.class })
    private Long exId;

    /**
     * 异常编码
     */
    private String exCode;

    /**
     * 异常类型
     */
    @NotBlank(message = "异常类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String etCode;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exName;

    /**
     * 已删除
     */
    @NotNull(message = "已删除不能为空", groups = { EditGroup.class })
    private Long exDelete;

    /**
     * 描述
     */
    private String exDesc;


}
