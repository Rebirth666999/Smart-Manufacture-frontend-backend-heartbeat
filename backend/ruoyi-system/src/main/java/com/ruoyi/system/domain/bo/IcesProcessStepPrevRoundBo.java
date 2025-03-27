package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 关联-工艺步骤的跨轮次前序步骤业务对象 ices_process_step_prev_round
 *
 * @author ruoyi
 * @date 2025-03-09
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesProcessStepPrevRoundBo extends BaseEntity {

    /**
     * 跨轮次前序步骤关联ID
     */
    @NotNull(message = "跨轮次前序步骤关联ID不能为空", groups = { EditGroup.class })
    private Long psprId;

    /**
     * 跨轮前序步骤关联编码
     */
    private String psprCode;

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
     * 跨轮次前序工艺步骤
     */
    @NotBlank(message = "跨轮次前序工艺步骤不能为空", groups = { AddGroup.class, EditGroup.class })
    private String psCodePrev;

    /**
     * 已删除
     */
    @NotNull(message = "已删除不能为空", groups = { EditGroup.class })
    private Long psprDelete;


}
