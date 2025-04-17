package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 产品仓库业务对象 ices_product_store
 *
 * @author ruoyi
 * @date 2025-04-17
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesProductStoreBo extends BaseEntity {

    /**
     * 产品仓库ID
     */
    @NotNull(message = "产品仓库ID不能为空", groups = { EditGroup.class })
    private Long prsId;

    /**
     * 产品仓库编码
     */
    private String prsCode;

    /**
     * 产品仓库名称
     */
    @NotBlank(message = "产品仓库名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String prsName;

    /**
     * 货位数量
     */
    @NotNull(message = "货位数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Float prsSpace;

    /**
     * 空闲货位数量
     */
    @NotNull(message = "空闲货位数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Float prsFree;

    /**
     * 已删除
     */
    private Long prsDelete;

    /**
     * 描述
     */
    private String prsDesc;


}
