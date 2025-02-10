package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 车间原料库存业务对象 ices_material_stock
 *
 * @author ruoyi
 * @date 2025-02-10
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesMaterialStockBo extends BaseEntity {

    /**
     * 车间原料库存ID
     */
    @NotNull(message = "车间原料库存ID不能为空", groups = { EditGroup.class })
    private Long msId;

    /**
     * 车间ID
     */
    @NotNull(message = "车间ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long arId;

    /**
     * 原料ID
     */
    @NotNull(message = "原料ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long maId;

    /**
     * 库存
     */
    @NotNull(message = "库存不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long msStock;

    /**
     * 已删除
     */
    private Long msDelete;


}
