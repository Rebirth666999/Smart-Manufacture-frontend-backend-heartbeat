package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常参数模板条目业务对象 ices_exception_param_map
 *
 * @author ruoyi
 * @date 2025-04-08
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesExceptionParamMapBo extends BaseEntity {

    /**
     * 模板条目ID
     */
    @NotNull(message = "模板条目ID不能为空", groups = { EditGroup.class })
    private Long expmId;

    /**
     * 模板条目编码
     */
    private String expmCode;

    /**
     * 异常参数模板
     */
    @NotBlank(message = "异常参数模板不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exptCode;

    /**
     * 源字段名
     */
    @NotBlank(message = "源字段名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String expmNameOrgn;

    /**
     * 源字段编码
     */
    @NotBlank(message = "源字段编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String expmCodeOrgn;

    /**
     * 目标字段名
     */
    @NotBlank(message = "目标字段名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String expName;

    /**
     * 目标字段编码
     */
    @NotBlank(message = "目标字段编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String expCode;

    /**
     * 默认值
     */
    private String expmDefault;

    /**
     * 转换公式
     */
    private String expmFormula;

    /**
     * 已删除
     */
    private Long expmDelete;

    /**
     * 描述
     */
    private String expmDesc;


}
