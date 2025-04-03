package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备事件日志业务对象 ices_equipment_record
 *
 * @author ruoyi
 * @date 2025-03-01
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesEquipmentRecordBo extends BaseEntity {

    /**
     * 事件日志ID
     */
    @NotNull(message = "事件日志ID不能为空", groups = { EditGroup.class })
    private Long erId;

    /**
     * 设备日志编码
     */
    private String erCode;

    /**
     * 关联设备
     */
    @NotBlank(message = "关联设备不能为空", groups = { EditGroup.class })
    private String eqCode;

    /**
     * 事件类型代码
     */
    @NotBlank(message = "事件类型代码不能为空", groups = { EditGroup.class })
    private String erType;

    /**
     * 事件状态代码
     */
    @NotBlank(message = "事件状态代码不能为空", groups = { EditGroup.class })
    private String erStat;

    /**
     * 发生时间
     */
    @NotNull(message = "发生时间不能为空", groups = { EditGroup.class })
    private Date erBegin;

    /**
     * 已删除
     */
    private Long erDelete;
    private Integer erLevel;
    /**
     * 描述
     */
    private String erDesc;


}
