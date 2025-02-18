package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备模型类型业务对象 ices_equipment_model_type
 *
 * @author ruoyi
 * @date 2025-02-18
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesEquipmentModelTypeBo extends BaseEntity {

    /**
     * 模型类型ID
     */
    @NotNull(message = "模型类型ID不能为空", groups = { EditGroup.class })
    private Long emtId;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String emtName;

    /**
     * 已删除
     */
    private Long emtDelete;

    /**
     * 描述
     */
    private String emtDesc;


}
