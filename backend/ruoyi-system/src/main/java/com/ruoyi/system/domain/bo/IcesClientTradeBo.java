package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 客户贸易信息业务对象 ices_client_trade
 *
 * @author ruoyi
 * @date 2025-03-28
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesClientTradeBo extends BaseEntity {

    /**
     * 贸易信息ID
     */
    @NotNull(message = "贸易信息ID不能为空", groups = { EditGroup.class })
    private Long ctId;

    /**
     * 贸易信息编码
     */
    private String ctCode;

    /**
     * 客户
     */
    @NotBlank(message = "客户不能为空", groups = { AddGroup.class, EditGroup.class })
    private String clCode;

    /**
     * 收货人
     */
    @NotBlank(message = "收货人不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ctName;

    /**
     * 收货地址
     */
    @NotBlank(message = "收货地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ctAddr;

    /**
     * 收货电话
     */
    @NotBlank(message = "收货电话不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ctPhone;

    /**
     * 国家
     */
    @NotBlank(message = "国家不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ctCountry;

    /**
     * 已删除
     */
    private Long ctDelete;

    /**
     * 描述
     */
    private String ctDesc;


}
