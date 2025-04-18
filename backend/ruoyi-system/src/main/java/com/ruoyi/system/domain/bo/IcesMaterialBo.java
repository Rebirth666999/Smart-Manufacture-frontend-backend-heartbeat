package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 原料业务对象 ices_material
 *
 * @author ruoyi
 * @date 2025-04-17
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesMaterialBo extends BaseEntity {

    /**
     * 原料ID
     */
    @NotNull(message = "原料ID不能为空", groups = { EditGroup.class })
    private Long maId;

    /**
     * 原料编码
     */
    private String maCode;

    /**
     * 原料名称
     */
    @NotBlank(message = "原料名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String maName;

    /**
     * 占用货位数量
     */
    @NotNull(message = "占用货位数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Float maOccupy;

    /**
     * 单位
     */
    @NotBlank(message = "单位不能为空", groups = { AddGroup.class, EditGroup.class })
    private String maUnit;

    /**
     * 已删除
     */
    private Long maDelete;

    /**
     * 描述
     */
    private String maDesc;


}
