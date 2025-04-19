package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 产品业务对象 ices_product
 *
 * @author ruoyi
 * @date 2025-04-17
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesProductBo extends BaseEntity {

    /**
     * 产品ID
     */
    @NotNull(message = "产品ID不能为空", groups = { EditGroup.class })
    private Long prId;

    /**
     * 产品编码
     */
    private String prCode;

    /**
     * 产品名称
     */
    @NotBlank(message = "产品名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String prName;

    /**
     * 占用货位数量
     */
    @NotNull(message = "占用货位数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Float prOccupy;

    /**
     * 定制详情
     */
    private String prCust;

    /**
     * 已删除
     */
    private Long prDelete;

    /**
     * 描述
     */
    private String prDesc;


}
