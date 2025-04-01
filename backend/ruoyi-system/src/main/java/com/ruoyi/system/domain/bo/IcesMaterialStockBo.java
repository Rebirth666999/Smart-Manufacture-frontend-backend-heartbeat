package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 关联-车间原料库存业务对象 ices_material_stock
 *
 * @author ruoyi
 * @date 2025-04-01
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
     * 仓库原料库存编码
     */
    private String msCode;

    /**
     * 仓库
     */
    @NotBlank(message = "仓库不能为空", groups = { AddGroup.class, EditGroup.class })
    private String stCode;

    /**
     * 物料
     */
    @NotBlank(message = "物料不能为空", groups = { AddGroup.class, EditGroup.class })
    private String maCode;

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
