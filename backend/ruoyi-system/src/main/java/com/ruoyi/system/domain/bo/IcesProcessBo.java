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
     * 工艺流程编码
     */
    private String procCode;

    /**
     * 目标产品
     */
    @NotBlank(message = "目标产品不能为空", groups = { AddGroup.class, EditGroup.class })
    private String prCode;

    /**
     * 工艺流程名称
     */
    @NotBlank(message = "工艺流程名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String procName;

    /**
     * 自动生成模型ID
     */
    private String procModel;

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


    /**
     * 创建人
     */
    private String procCman;
    /**
     * 创建时间
     */
    private String procCdate;
    /**
     * 发布人
     */
    private String procRman;
    /**
     * 发布时间
     */
    private String procRdate;
    /**
     * 修改人
     */
    private String procMman;
    /**
     * 修改时间
     */
    private String procMdate;
}
