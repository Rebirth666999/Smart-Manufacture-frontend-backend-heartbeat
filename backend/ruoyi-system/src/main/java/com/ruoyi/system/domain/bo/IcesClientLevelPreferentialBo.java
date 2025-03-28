package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 关联-客户等级对应的优惠策略业务对象 ices_client_level_preferential
 *
 * @author ruoyi
 * @date 2025-03-27
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesClientLevelPreferentialBo extends BaseEntity {

    /**
     * 对应关系ID
     */
    @NotNull(message = "对应关系ID不能为空", groups = { EditGroup.class })
    private Long clpId;

    /**
     * 对应关系编码
     */
    @NotBlank(message = "对应关系编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String clpCode;

    /**
     * 客户等级
     */
    @NotBlank(message = "客户等级不能为空", groups = { AddGroup.class, EditGroup.class })
    private String cllCode;

    /**
     * 优惠策略
     */
    @NotBlank(message = "优惠策略不能为空", groups = { AddGroup.class, EditGroup.class })
    private String cpCode;

    /**
     * 已删除
     */
    @NotNull(message = "已删除不能为空", groups = { EditGroup.class })
    private Long clpDelete;


}
