package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 仓库产品库存业务对象 ices_product_stock
 *
 * @author ruoyi
 * @date 2025-04-18
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesProductStockBo extends BaseEntity {

    /**
     * 仓库产品库存ID
     */
    @NotNull(message = "仓库产品库存ID不能为空", groups = { EditGroup.class })
    private Long pssId;

    /**
     * 仓库产品库存编码
     */
    private String pssCode;

    /**
     * 仓库
     */
    @NotBlank(message = "仓库不能为空", groups = { AddGroup.class, EditGroup.class })
    private String prsCode;

    /**
     * 产品
     */
    @NotBlank(message = "产品不能为空", groups = { AddGroup.class, EditGroup.class })
    private String prCode;

    /**
     * 库存量
     */
    @NotNull(message = "库存量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Float pssStock;

    /**
     * 已删除
     */
    private Long pssDelete;


}
