package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常预警配置业务对象 ices_exception_warning
 *
 * @author ruoyi
 * @date 2025-04-09
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesExceptionWarningBo extends BaseEntity {

    /**
     * 预警配置ID
     */
    @NotNull(message = "预警配置ID不能为空", groups = { EditGroup.class })
    private Long exwId;

    /**
     * 预警配置编码
     */
    private String exwCode;

    /**
     * 异常
     */
    @NotBlank(message = "异常不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exCode;

    /**
     * 异常源
     */
    @NotBlank(message = "异常源不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exsCode;

    /**
     * 时间表达式
     */
    @NotBlank(message = "时间表达式不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exwFormula;

    /**
     * 消息接收人
     */
    @NotNull(message = "消息接收人不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long exwRecv;

    /**
     * 消息通道
     */
    @NotBlank(message = "消息通道不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exwTunnel;

    /**
     * 消息模板
     */
    @NotBlank(message = "消息模板不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exwTemplate;

    /**
     * 是否触发源系统预警
     */
    @NotBlank(message = "是否触发源系统预警不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exwWarningOrgn;

    /**
     * 是否触发源系统处理
     */
    @NotBlank(message = "是否触发源系统处理不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exwHandleOrgn;

    /**
     * 已删除
     */
    private Long exwDelete;


}
