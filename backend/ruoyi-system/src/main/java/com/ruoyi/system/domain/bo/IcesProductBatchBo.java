package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 实际产品批次编码业务对象 ices_product_batch
 *
 * @author ruoyi
 * @date 2025-04-19
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesProductBatchBo extends BaseEntity {

    /**
     * 产品批次ID
     */
    @NotNull(message = "产品批次ID不能为空", groups = { EditGroup.class })
    private Long pbId;

    /**
     * 产品批次编码
     */
    private String pbCode;

    /**
     * 订单产品需求
     */
    @NotBlank(message = "订单产品需求不能为空", groups = { AddGroup.class, EditGroup.class })
    private String odCode;

    /**
     * 批次编号
     */
    @NotBlank(message = "批次编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String pbBatch;

    /**
     * 起始号码
     */
    @NotNull(message = "起始号码不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long pbStart;

    /**
     * 终止号码
     */
    @NotNull(message = "终止号码不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long pbEnd;

    /**
     * 已删除
     */
    private Long pbDelete;


}
