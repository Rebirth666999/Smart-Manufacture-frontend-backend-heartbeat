package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常生命周期版本业务对象 ices_exception_lifecycle_version
 *
 * @author ruoyi
 * @date 2025-04-08
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesExceptionLifecycleVersionBo extends BaseEntity {

    /**
     * 生命周期版本ID
     */
    @NotNull(message = "生命周期版本ID不能为空", groups = { EditGroup.class })
    private Long exlvId;

    /**
     * 生命周期版本编码
     */
    @NotBlank(message = "生命周期版本编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exlvCode;

    /**
     * 所属生命周期
     */
    @NotBlank(message = "所属生命周期不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exlCode;

    /**
     * 版本名称
     */
    @NotBlank(message = "版本名称不能为空", groups = { AddGroup.class, EditGroup.class })
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
