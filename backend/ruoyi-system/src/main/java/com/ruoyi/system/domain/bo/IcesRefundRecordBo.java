package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 退货记录业务对象 ices_refund_record
 *
 * @author ruoyi
 * @date 2025-04-19
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesRefundRecordBo extends BaseEntity {

    /**
     * 退货记录ID
     */
    @NotNull(message = "退货记录ID不能为空", groups = { EditGroup.class })
    private Long rrId;

    /**
     * 退货记录编码
     */
    private String rrCode;

    /**
     * 订单
     */
    @NotBlank(message = "订单不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orCode;

    /**
     * 产品
     */
    @NotBlank(message = "产品不能为空", groups = { AddGroup.class, EditGroup.class })
    private String prCode;

    /**
     * 客户
     */
    @NotBlank(message = "客户不能为空", groups = { AddGroup.class, EditGroup.class })
    private String clCode;

    /**
     * 数量
     */
    @NotNull(message = "数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long rrCount;

    /**
     * 已删除
     */
    private Long rrDelete;

    /**
     * 描述
     */
    private String rrDesc;

    /**
     * 办理人
     */
    private String rrMan;

    /**
     * 办理日期
     */
    private String rrDate;


}
