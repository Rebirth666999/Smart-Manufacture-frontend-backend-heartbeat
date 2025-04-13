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
 * 订单业务对象 ices_order
 *
 * @author ruoyi
 * @date 2025-03-17
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesOrderBo extends BaseEntity {

    /**
     * 订单ID
     */
    @NotNull(message = "订单ID不能为空", groups = { EditGroup.class })
    private Long orId;

    /**
     * 订单编码
     */
    private String orCode;

    /**
     * 客户
     */
    @NotBlank(message = "客户不能为空", groups = { AddGroup.class, EditGroup.class })
    private String clCode;

    /**
     * 订单名称
     */
    @NotBlank(message = "订单名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orName;

    /**
     * 状态代码
     */
    private String orStat;

    /**
     * 订单优先级
     */
    @NotNull(message = "订单优先级不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long orPriority;

    /**
     * 截止时间
     */
    @NotNull(message = "截止时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date orDeadline;

    /**
     * 总价
     */
    @NotNull(message = "总价不能为空", groups = { AddGroup.class, EditGroup.class })
    private Float orPrice;

    /**
     * 收货人
     */
    @NotBlank(message = "收货人不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orRecv;
    /**
     * 收货地址
     */
    @NotBlank(message = "收货地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orAddr;

    /**
     * 已删除
     */
    private Long orDelete;

    /**
     * 描述
     */
    private String orDesc;


}
