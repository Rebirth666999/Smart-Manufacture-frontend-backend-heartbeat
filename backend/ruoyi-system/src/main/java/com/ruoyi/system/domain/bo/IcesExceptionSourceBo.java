package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常源业务对象 ices_exception_source
 *
 * @author ruoyi
 * @date 2025-04-07
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesExceptionSourceBo extends BaseEntity {

    /**
     * 异常源ID
     */
    @NotNull(message = "异常源ID不能为空", groups = { EditGroup.class })
    private Long exsId;

    /**
     * 异常源编码
     */
    private String exsCode;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String exsName;

    /**
     * 认证token
     */
    private String exsToken;

    /**
     * 巡检接口
     */
    private String exsInspectionUrl;

    /**
     * 处理接口
     */
    private String exsHandleUrl;

    /**
     * 状态检查接口
     */
    private String exsStateUrl;

    /**
     * 预警接口
     */
    private String exsNotifyUrl;

    /**
     * 已删除
     */
    @NotNull(message = "已删除不能为空", groups = { EditGroup.class })
    private Long exsDelete;

    /**
     * 描述
     */
    private String exsDesc;


}
