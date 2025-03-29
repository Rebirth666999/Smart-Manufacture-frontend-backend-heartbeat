package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备模型操作业务对象 ices_model_operation
 *
 * @author ruoyi
 * @date 2025-02-28
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesModelOperationBo extends BaseEntity {

    /**
     * 模型操作ID
     */
    @NotNull(message = "模型操作ID不能为空", groups = { EditGroup.class })
    private Long moId;

    /**
     * 模型操作编码
     */
    private String moCode;

    /**
     * 所属设备模型
     */
    @NotBlank(message = "所属设备模型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String emCode;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String moName;

    /**
     * 已删除
     */
    private Long moDelete;

    /**
     * 描述
     */
    private String moDesc;


}
