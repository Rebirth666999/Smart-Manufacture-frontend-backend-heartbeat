package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 物料业务对象 ices_material
 *
 * @author ruoyi
 * @date 2025-03-26
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesMaterialBo extends BaseEntity {

    /**
     * 物料ID
     */
    @NotNull(message = "物料ID不能为空", groups = { EditGroup.class })
    private Long maId;

    /**
     * 物料编码
     */
    private String maCode;

    /**
     * 物料名称
     */
    @NotBlank(message = "物料名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String maName;

    /**
     * 物料类型
     */
    @NotBlank(message = "物料类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String maType;

    /**
     * 占用货位数量
     */
    @NotNull(message = "占用货位数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Float maOccupy;

    /**
     * 已删除
     */
    private Long maDelete;

    /**
     * 描述
     */
    private String maDesc;


}
