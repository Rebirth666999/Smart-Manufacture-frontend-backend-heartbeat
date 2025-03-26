package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 主控节点业务对象 ices_area_control
 *
 * @author ruoyi
 * @date 2025-02-19
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesAreaControlBo extends BaseEntity {

    /**
     * 主控节点ID
     */
    @NotNull(message = "主控节点ID不能为空", groups = { EditGroup.class })
    private Long acId;

    /**
     * 主控节点编码
     */
    private String acCode;

    /**
     * 所属车间
     */
    @NotBlank(message = "所属车间不能为空", groups = { AddGroup.class, EditGroup.class })
    private String arCode;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String acName;

    /**
     * IP地址
     */
    @NotBlank(message = "IP地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String acIp;

    /**
     * 已删除
     */
    private Long acDelete;

    /**
     * 描述
     */
    private String acDesc;


}
