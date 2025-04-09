package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常处理日志业务对象 ices_exception_record_log
 *
 * @author ruoyi
 * @date 2025-04-09
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesExceptionRecordLogBo extends BaseEntity {

    /**
     * 处理日志ID
     */
    @NotNull(message = "处理日志ID不能为空", groups = { EditGroup.class })
    private Long exrlId;

    /**
     * 处理日志编码
     */
    private String exrlCode;

    /**
     * 所属异常记录
     */
    @NotBlank(message = "所属异常记录不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exrCode;

    /**
     * 处理人
     */
    @NotNull(message = "处理人不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long exrlUserHandle;

    /**
     * 处理时间
     */
    @NotBlank(message = "处理时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exrlTime;

    /**
     * 处理意见
     */
    @NotBlank(message = "处理意见不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exrlResult;

    /**
     * 已删除
     */
    private Long exrlDelete;


}
