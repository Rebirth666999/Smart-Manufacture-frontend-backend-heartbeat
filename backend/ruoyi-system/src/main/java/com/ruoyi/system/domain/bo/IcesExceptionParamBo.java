package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常参数业务对象 ices_exception_param
 *
 * @author ruoyi
 * @date 2025-04-07
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesExceptionParamBo extends BaseEntity {

    /**
     * 异常参数ID
     */
    @NotNull(message = "异常参数ID不能为空", groups = { EditGroup.class })
    private Long expId;

    /**
     * 异常参数编码
     */
    private String expCode;

    /**
     * 所属异常
     */
    @NotBlank(message = "所属异常不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exCode;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String expName;

    /**
     * 类型
     */
    @NotBlank(message = "类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String expType;

    /**
     * 已删除
     */
    @NotNull(message = "已删除不能为空", groups = { EditGroup.class })
    private Long expDelete;


}
