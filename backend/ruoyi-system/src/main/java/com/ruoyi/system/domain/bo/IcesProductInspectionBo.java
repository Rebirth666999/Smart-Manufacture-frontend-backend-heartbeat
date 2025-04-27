package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 产品质检单业务对象 ices_product_inspection
 *
 * @author ruoyi
 * @date 2025-04-19
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesProductInspectionBo extends BaseEntity {

    /**
     * 质检单ID
     */
    @NotNull(message = "质检单ID不能为空", groups = { EditGroup.class })
    private Long piId;

    /**
     * 质检单编码
     */
    private String piCode;

    /**
     * 批次
     */
    @NotBlank(message = "批次不能为空", groups = { AddGroup.class, EditGroup.class })
    private String pbCode;

    /**
     * 负责人
     */
    private String piMan;

    /**
     * 开始日期
     */
    private String piSdate;

    /**
     * 部门
     */
    private String piDept;

    /**
     * 结束日期
     */
    private String piEdate;

    /**
     * 已删除
     */
    private Long piDelete;


}
