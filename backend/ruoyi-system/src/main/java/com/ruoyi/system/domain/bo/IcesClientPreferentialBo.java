package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 客户优惠策略业务对象 ices_client_preferential
 *
 * @author ruoyi
 * @date 2025-03-24
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesClientPreferentialBo extends BaseEntity {

    /**
     * 优惠策略ID
     */
    @NotNull(message = "优惠策略ID不能为空", groups = { EditGroup.class })
    private Long cpId;

    /**
     * 优惠策略编码
     */
    private String cpCode;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String cpName;

    /**
     * 类别
     */
    @NotBlank(message = "类别不能为空", groups = { AddGroup.class, EditGroup.class })
    private String cpType;

    /**
     * 已删除
     */
    @NotNull(message = "已删除不能为空", groups = { EditGroup.class })
    private Long cpDelete;

    /**
     * 描述
     */
    private String cpDesc;


}
