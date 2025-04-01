package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 原料台账业务对象 ices_material_record
 *
 * @author ruoyi
 * @date 2025-04-01
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesMaterialRecordBo extends BaseEntity {

    /**
     * 原料台账ID
     */
    @NotNull(message = "原料台账ID不能为空", groups = { EditGroup.class })
    private Long mrId;

    /**
     * 台账编码
     */
    private String mrCode;

    /**
     * 物料
     */
    @NotBlank(message = "物料不能为空", groups = { AddGroup.class, EditGroup.class })
    private String maCode;

    /**
     * 仓库
     */
    @NotBlank(message = "仓库不能为空", groups = { AddGroup.class, EditGroup.class })
    private String stCode;

    /**
     * 变动类型
     */
    @NotBlank(message = "变动类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String mrType;

    /**
     * 台账状态
     */
    @NotBlank(message = "台账状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String mrStat;

    /**
     * 预计变动值
     */
    @NotNull(message = "预计变动值不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long mrEst;

    /**
     * 实际变动值
     */
    private Long mrReal;

    /**
     * 已删除
     */
    @NotNull(message = "已删除不能为空", groups = { EditGroup.class })
    private Long mrDelete;

    /**
     * 描述
     */
    private String mrDesc;


}
