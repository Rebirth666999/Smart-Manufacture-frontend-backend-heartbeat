package com.ruoyi.system.domain.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备原子操作业务对象 ices_equipment_atom_operation
 *
 * @author ruoyi
 * @date 2025-03-02
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesEquipmentAtomOperationBo extends BaseEntity {

    /**
     * 设备原子操作ID
     */
    @NotNull(message = "设备原子操作ID不能为空", groups = { EditGroup.class })
    private Long eaoId;

    /**
     * 所属设备ID
     */
    @NotNull(message = "所属设备ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long eqId;

    /**
     * 原子操作名称
     */
    @NotBlank(message = "操作名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String eaoName;

    /**
     * 操作类型
     */
    @NotBlank(message = "操作类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String eaoType;

    /**
     * 请求方法
     */
    @NotBlank(message = "请求方法不能为空", groups = { AddGroup.class, EditGroup.class })
    private String eaoRequestType;

    /**
     * URL
     */
    @NotBlank(message = "URL不能为空", groups = { AddGroup.class, EditGroup.class })
    private String eaoUrl;

    /**
     * 执行时长
     */
    private Float eaoExecTime;

    /**
     * 已删除
     */
    private Long eaoDelete;

    /**
     * 描述
     */
    private String eaoDesc;


}
