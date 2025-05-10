package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常记录业务对象 ices_exception_record
 *
 * @author ruoyi
 * @date 2025-04-09
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesExceptionRecordBo extends BaseEntity {

    /**
     * 异常记录ID
     */
    @NotNull(message = "异常记录ID不能为空", groups = { EditGroup.class })
    private Long exrId;

    /**
     * 异常记录编码
     */
    private String exrCode;

    /**
     * 异常源
     */
    @NotBlank(message = "异常源不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exsCode;

    /**
     * 异常
     */
    @NotBlank(message = "异常不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exCode;

    /**
     * 描述
     */
    @NotBlank(message = "描述不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exrDesc;

    /**
     * 参数
     */
    @NotBlank(message = "参数不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exrParam;

    /**
     * 状态
     */
    @NotBlank(message = "状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exrStat;

    /**
     * 等级
     */
    @NotBlank(message = "等级不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exrLevel;

    /**
     * 异常上报人
     */
    private String exrUserReport;

    /**
     * 当前处理人
     */
    private String exrUserHandle;

    /**
     * 异常解除人
     */
    private String exrUserFinish;

    /**
     * 异常责任人
     */
    private String exrUserResp;

    /**
     * 上报时间
     */
    private String exrCdate;

    /**
     * 影响因子
     */
    @NotNull(message = "影响因子不能为空", groups = { AddGroup.class, EditGroup.class })
    private Float exrImpactFactor;

    /**
     * 影响等级
     */
    @NotBlank(message = "影响等级不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exrImpactLevel;

    /**
     * 异常执行流程
     */
    private String exrProcess;

    /**
     * 已删除
     */
    private Long exrDelete;


}
