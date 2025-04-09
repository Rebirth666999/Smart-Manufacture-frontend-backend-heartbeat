package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常生命周期业务对象 ices_exception_lifecycle
 *
 * @author ruoyi
 * @date 2025-04-08
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesExceptionLifecycleBo extends BaseEntity {

    /**
     * 生命周期ID
     */
    @NotNull(message = "生命周期ID不能为空", groups = { EditGroup.class })
    private Long exlId;

    /**
     * 生命周期编码
     */
    private String exlCode;

    /**
     * 所属异常
     */
    @NotBlank(message = "所属异常不能为空", groups = { AddGroup.class, EditGroup.class })
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
