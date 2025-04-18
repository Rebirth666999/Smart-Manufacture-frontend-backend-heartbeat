package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 原料仓库业务对象 ices_material_store
 *
 * @author ruoyi
 * @date 2025-04-17
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesMaterialStoreBo extends BaseEntity {

    /**
     * 原料仓库ID
     */
    @NotNull(message = "原料仓库ID不能为空", groups = { EditGroup.class })
    private Long msId;

    /**
     * 原料仓库编码
     */
    private String msCode;

    /**
     * 仓库名称
     */
    @NotBlank(message = "仓库名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String msName;

    /**
     * 货位数量
     */
    @NotNull(message = "货位数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Float msSpace;

    /**
     * 空闲货位数量
     */
    @NotNull(message = "空闲货位数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Float msFree;

    /**
     * 已删除
     */
    private Long msDelete;

    /**
     * 描述
     */
    private String msDesc;


}
