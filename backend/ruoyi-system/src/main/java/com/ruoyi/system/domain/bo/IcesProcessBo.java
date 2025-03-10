package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 工艺流程业务对象 ices_process
 *
 * @author ruoyi
 * @date 2025-03-10
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesProcessBo extends BaseEntity {

    /**
     * 工艺流程ID
     */
    @NotNull(message = "工艺流程ID不能为空", groups = { EditGroup.class })
    private Long procId;

    /**
     * 目标产品ID
     */
    @NotNull(message = "目标产品ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long prId;

    /**
     * 工艺流程名称
     */
    @NotBlank(message = "工艺流程名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String procName;

    /**
     * 工艺流程状态
     */
    private String procStat;

    /**
     * 已删除
     */
    private Long procDelete;

    /**
     * 描述
     */
    private String procDesc;


}
