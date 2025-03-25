package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 客户等级业务对象 ices_client_level
 *
 * @author ruoyi
 * @date 2025-03-24
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesClientLevelBo extends BaseEntity {

    /**
     * 客户等级ID
     */
    @NotNull(message = "客户等级ID不能为空", groups = { EditGroup.class })
    private Long cllId;

    /**
     * 客户等级标识
     */
    @NotBlank(message = "客户等级标识不能为空", groups = { AddGroup.class, EditGroup.class })
    private String cllLabel;

    /**
     * 客户等级名称
     */
    @NotBlank(message = "客户等级名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String cllName;

    /**
     * 已删除
     */
    @NotNull(message = "已删除不能为空", groups = { EditGroup.class })
    private Long cllDelete;

    /**
     * 描述
     */
    private String cllDesc;


}
