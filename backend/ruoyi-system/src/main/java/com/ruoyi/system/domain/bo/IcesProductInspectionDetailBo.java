package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 产品质检单明细业务对象 ices_product_inspection_detail
 *
 * @author ruoyi
 * @date 2025-04-19
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesProductInspectionDetailBo extends BaseEntity {

    /**
     * 质检单明细ID
     */
    @NotNull(message = "质检单明细ID不能为空", groups = { EditGroup.class })
    private Long pidId;

    /**
     * 质检单明细编码
     */
    private String pidCode;

    /**
     * 所属质检单
     */
    @NotBlank(message = "所属质检单不能为空", groups = { AddGroup.class, EditGroup.class })
    private String piCode;

    /**
     * 产品类型
     */
    @NotBlank(message = "产品类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String prCode;

    /**
     * 产品编码
     */
    @NotBlank(message = "产品编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String pidBatchNum;

    /**
     * 质检结果
     */
    private String pidResult;

    /**
     * 合格标志
     */
    private String pidFlag;

    /**
     * 质检人
     */
    private String pidMan;

    /**
     * 质检日期
     */
    private String pidDate;

    /**
     * 已删除
     */
    private Long pidDelete;


}
