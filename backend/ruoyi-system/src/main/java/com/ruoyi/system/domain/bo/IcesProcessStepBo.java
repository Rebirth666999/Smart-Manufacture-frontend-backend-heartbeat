package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 工艺步骤业务对象 ices_process_step
 *
 * @author ruoyi
 * @date 2025-03-09
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesProcessStepBo extends BaseEntity {

    /**
     * 工艺步骤ID
     */
    @NotNull(message = "工艺步骤ID不能为空", groups = { EditGroup.class })
    private Long psId;

    /**
     * 工艺步骤编码
     */
    private String psCode;

    /**
     * 所属工艺流程
     */
    @NotBlank(message = "所属工艺流程不能为空", groups = { AddGroup.class, EditGroup.class })
    private String procCode;

    /**
     * 设备模型操作
     */
    @NotBlank(message = "设备模型操作不能为空", groups = { AddGroup.class, EditGroup.class })
    private String moCode;

    /**
     * 自动生成活动ID
     */
    private String psModel;

    /**
     * 已删除
     */
    @NotNull(message = "已删除不能为空", groups = { EditGroup.class })
    private Long psDelete;

    /**
     * 描述
     */
    private String psDesc;


}
