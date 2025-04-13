package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常预警记录业务对象 ices_exception_warning_record
 *
 * @author ruoyi
 * @date 2025-04-09
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesExceptionWarningRecordBo extends BaseEntity {

    /**
     * 预警记录ID
     */
    @NotNull(message = "预警记录ID不能为空", groups = { EditGroup.class })
    private Long exwrId;

    /**
     * 预警记录编码
     */
    private String exwrCode;

    /**
     * 预警配置
     */
    @NotBlank(message = "预警配置不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exwCode;

    /**
     * 检查时间
     */
    @NotBlank(message = "检查时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exwrTime;

    /**
     * 检查结果
     */
    @NotBlank(message = "检查结果不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exwrResult;

    /**
     * 是否产生预警
     */
    @NotBlank(message = "是否产生预警不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exwrWarning;

    /**
     * 已删除
     */
    private Long exwrDelete;


}
