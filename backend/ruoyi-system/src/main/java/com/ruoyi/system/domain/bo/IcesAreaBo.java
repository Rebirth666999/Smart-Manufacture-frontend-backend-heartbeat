package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 车间业务对象 ices_area
 *
 * @author ruoyi
 * @date 2025-02-09
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesAreaBo extends BaseEntity {

    /**
     * 车间ID
     */
    @NotNull(message = "车间ID不能为空", groups = { EditGroup.class })
    private Long arId;

    /**
     * 车间名称
     */
    @NotBlank(message = "车间名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String arName;

    /**
     * 已删除
     */
    private Long arDelete;

    /**
     * 描述
     */
    private String arDesc;


}
