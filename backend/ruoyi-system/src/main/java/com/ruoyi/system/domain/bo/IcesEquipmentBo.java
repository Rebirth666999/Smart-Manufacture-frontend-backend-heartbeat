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
     * 设备编码
     */
    private String eqCode;

    /**
     * 所属车间
     */
    @NotBlank(message = "所属车间不能为空", groups = { AddGroup.class, EditGroup.class })
    private String arCode;

    /**
     * 所属设备模型
     */
    @NotBlank(message = "所属设备模型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String emCode;

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
    /*
     * 设备测试用flaskip
     */
    private String eqFlaskIp;
    /**
     * 已删除
     */
    private Long eqDelete;

    /**
     * 描述
     */
    private String eqDesc;

    /**
     * 创建人
     */
    private String eqCman;
    /**
     * 创建时间
     */
    private String eqCdate;
    /**
     * 发布人
     */
    private String eqRman;
    /**
     * 发布时间
     */
    private String eqRdate;
    /**
     * 修改人
     */
    private String eqMman;
    /**
     * 修改时间
     */
    private String eqMdate;


}
