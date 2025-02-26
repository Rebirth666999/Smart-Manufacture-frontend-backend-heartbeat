package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备模型业务对象 ices_equipment_model
 *
 * @author ruoyi
 * @date 2025-02-26
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesEquipmentModelBo extends BaseEntity {

    /**
     * 设备模型ID
     */
    @NotNull(message = "设备模型ID不能为空", groups = { EditGroup.class })
    private Long emId;

    /**
     * 所属模型类型ID
     */
    @NotNull(message = "所属模型类型ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long emtId;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String emName;

    /**
     * 状态
     */
    private String emStat;

    /**
     * 已删除
     */
    private Long emDelete;

    /**
     * 描述
     */
    private String emDesc;


}
