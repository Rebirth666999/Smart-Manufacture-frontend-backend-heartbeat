package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常参数模板业务对象 ices_exception_param_template
 *
 * @author ruoyi
 * @date 2025-04-07
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesExceptionParamTemplateBo extends BaseEntity {

    /**
     * 参数模板ID
     */
    @NotNull(message = "参数模板ID不能为空", groups = { EditGroup.class })
    private Long exptId;

    /**
     * 参数模板编码
     */
    private String exptCode;

    /**
     * 异常
     */
    @NotBlank(message = "异常不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exCode;

    /**
     * 异常源
     */
    @NotBlank(message = "异常源不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exsCode;

    /**
     * 已删除
     */
    private Long exptDelete;

    /**
     * 描述
     */
    private String exptDesc;


}
