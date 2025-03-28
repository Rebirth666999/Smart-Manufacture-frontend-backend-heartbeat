package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 关联-工艺步骤的前序步骤业务对象 ices_process_step_prev
 *
 * @author ruoyi
 * @date 2025-03-09
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesProcessStepPrevBo extends BaseEntity {

    /**
     * 前序步骤关联ID
     */
    @NotNull(message = "前序步骤关联ID不能为空", groups = { EditGroup.class })
    private Long pspId;

    /**
     * 跨轮前序步骤关联编码
     */
    private String pspCode;

    /**
     * 所属工艺流程
     */
    @NotBlank(message = "所属工艺流程不能为空", groups = { AddGroup.class, EditGroup.class })
    private String procCode;

    /**
     * 当前工艺步骤
     */
    @NotBlank(message = "当前工艺步骤不能为空", groups = { AddGroup.class, EditGroup.class })
    private String psCodeCur;

    /**
     * 前序工艺步骤
     */
    @NotBlank(message = "前序工艺步骤不能为空", groups = { AddGroup.class, EditGroup.class })
    private String psCodePrev;

    /**
     * 已删除
     */
    @NotNull(message = "已删除不能为空", groups = { EditGroup.class })
    private Long pspDelete;


    public void setPsIds(List<Long> toDelete) {
    }
}
