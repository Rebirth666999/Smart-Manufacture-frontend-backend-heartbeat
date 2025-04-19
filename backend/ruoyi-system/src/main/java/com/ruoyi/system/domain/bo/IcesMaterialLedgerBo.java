package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 原料台账业务对象 ices_material_ledger
 *
 * @author ruoyi
 * @date 2025-04-18
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesMaterialLedgerBo extends BaseEntity {

    /**
     * 台账ID
     */
    @NotNull(message = "台账ID不能为空", groups = { EditGroup.class })
    private Long mlId;

    /**
     * 台账编码
     */
    private String mlCode;

    /**
     * 原料
     */
    @NotBlank(message = "原料不能为空", groups = { AddGroup.class, EditGroup.class })
    private String maCode;

    /**
     * 库存量
     */
    @NotNull(message = "库存量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Float mlStock;

    /**
     * 已删除
     */
    private Long mlDelete;

    /**
     * 描述
     */
    private String mlDesc;


}
