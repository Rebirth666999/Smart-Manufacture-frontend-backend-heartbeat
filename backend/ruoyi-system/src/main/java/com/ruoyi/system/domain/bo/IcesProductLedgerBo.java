package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 产品台账业务对象 ices_product_ledger
 *
 * @author ruoyi
 * @date 2025-04-18
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesProductLedgerBo extends BaseEntity {

    /**
     * 台账ID
     */
    @NotNull(message = "台账ID不能为空", groups = { EditGroup.class })
    private Long plId;

    /**
     * 台账编码
     */
    private String plCode;

    /**
     * 产品
     */
    @NotBlank(message = "产品不能为空", groups = { AddGroup.class, EditGroup.class })
    private String prCode;

    /**
     * 库存量
     */
    @NotNull(message = "库存量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Float plStock;

    /**
     * 已删除
     */
    private Long plDelete;

    /**
     * 描述
     */
    private String plDesc;


}
