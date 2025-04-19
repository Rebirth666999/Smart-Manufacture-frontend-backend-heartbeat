package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 关联-工艺流程原料需求业务对象 ices_process_material
 *
 * @author ruoyi
 * @date 2025-03-12
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesProcessMaterialBo extends BaseEntity {

    /**
     * 原料需求ID
     */
    @NotNull(message = "原料需求ID不能为空", groups = { EditGroup.class })
    private Long pmId;

    /**
     * 原料需求编码
     */
    private String pmCode;

    /**
     * 所属工艺流程
     */
    @NotBlank(message = "所属工艺流程不能为空", groups = { AddGroup.class, EditGroup.class })
    private String procCode;

    /**
     * 所属工艺步骤
     */
    @NotBlank(message = "所属工艺步骤不能为空", groups = { AddGroup.class, EditGroup.class })
    private String psCode;

    /**
     * 所用原料
     */
    @NotBlank(message = "所用原料不能为空", groups = { AddGroup.class, EditGroup.class })
    private String maCode;

    /**
     * 所需原料数量
     */
    @NotNull(message = "所需原料数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Float pmDemand;

    /**
     * 单位
     */
    @NotNull(message = "单位不能为空", groups = { AddGroup.class, EditGroup.class })
    private String pmUnit;

    /**
     * 已删除
     */
    private Long pmDelete;


}
