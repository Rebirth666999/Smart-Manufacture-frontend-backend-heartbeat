package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单所需产品关联业务对象 ices_order_demand
 *
 * @author ruoyi
 * @date 2025-04-10
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesOrderDemandBo extends BaseEntity {

    /**
     * 所需产品ID
     */
    @NotNull(message = "所需产品ID不能为空", groups = { EditGroup.class })
    private Long odId;

    /**
     * 所需产品编码
     */
    private String odCode;

    /**
     * 所需产品
     */
    @NotBlank(message = "所需产品不能为空", groups = { AddGroup.class, EditGroup.class })
    private String maCode;

    /**
     * 订单
     */
    @NotBlank(message = "订单不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orCode;

    /**
     * 所需产品数量
     */
    @NotNull(message = "所需产品数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long odDemand;

    /**
     * 已删除
     */
    private Long odDelete;


}
