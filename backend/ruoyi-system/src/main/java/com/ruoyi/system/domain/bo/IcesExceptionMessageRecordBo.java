package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常消息记录业务对象 ices_exception_message_record
 *
 * @author ruoyi
 * @date 2025-04-09
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesExceptionMessageRecordBo extends BaseEntity {

    /**
     * 消息记录ID
     */
    @NotNull(message = "消息记录ID不能为空", groups = { EditGroup.class })
    private Long exmrId;

    /**
     * 消息记录编码
     */
    private String exmrCode;

    /**
     * 预警配置
     */
    @NotBlank(message = "预警配置不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exwCode;

    /**
     * 预警记录
     */
    @NotBlank(message = "预警记录不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exwrCode;

    /**
     * 消息接收人
     */
    @NotNull(message = "消息接收人不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long exmrRecv;

    /**
     * 内容描述
     */
    @NotBlank(message = "内容描述不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exmrDesc;

    /**
     * 消息发送时间
     */
    @NotBlank(message = "消息发送时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exmrTime;

    /**
     * 消息发送结果
     */
    @NotBlank(message = "消息发送结果不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exmrResult;

    /**
     * 已删除
     */
    private Long exmrDelete;


}
