package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 仓库业务对象 ices_store
 *
 * @author ruoyi
 * @date 2025-03-26
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesStoreBo extends BaseEntity {

    /**
     * 仓库ID
     */
    @NotNull(message = "仓库ID不能为空", groups = { EditGroup.class })
    private Long stId;

    /**
     * 仓库编码
     */
    private String stCode;

    /**
     * 仓库名称
     */
    @NotBlank(message = "仓库名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String stName;

    /**
     * 仓库类型
     */
    @NotBlank(message = "仓库类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String stType;

    /**
     * 货位数量
     */
    @NotNull(message = "货位数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Float stSpace;

    /**
     * 空闲货位数量
     */
    @NotNull(message = "空闲货位数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Float stFree;

    /**
     * 已删除
     */
    private Long stDelete;

    /**
     * 描述
     */
    private String stDesc;


}
