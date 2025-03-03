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
 * 设备业务对象 ices_equipment
 *
 * @author ruoyi
 * @date 2025-02-28
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesEquipmentBo extends BaseEntity {

    /**
     * 设备ID
     */
    @NotNull(message = "设备ID不能为空", groups = { EditGroup.class })
    private Long eqId;

    /**
     * 所属车间ID
     */
    @NotNull(message = "所属车间ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long arId;

    /**
     * 所属设备模型ID
     */
    @NotNull(message = "所属设备模型ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long emId;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String eqName;

    /**
     * 状态代码
     */
    @NotBlank(message = "状态代码不能为空", groups = { EditGroup.class })
    private String eqStat;

    /**
     * 采购时间
     */
    private Date eqIntroduceTime;

    /**
     * IP地址
     */
    @NotBlank(message = "IP地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String eqIp;

    /**
     * 已删除
     */
    private Long eqDelete;

    /**
     * 描述
     */
    private String eqDesc;


}
