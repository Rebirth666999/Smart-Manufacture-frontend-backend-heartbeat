package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 业务编码业务对象 ices_code
 *
 * @author ruoyi
 * @date 2025-03-26
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesCodeBo extends BaseEntity {

    /**
     * 编码ID
     */
    @NotNull(message = "编码ID不能为空", groups = { EditGroup.class })
    private Long coId;

    /**
     * 编码类别
     */
    @NotBlank(message = "编码类别不能为空", groups = { AddGroup.class, EditGroup.class })
    private String coType;

    /**
     * 编码值
     */
    @NotNull(message = "编码值不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long coNum;

    /**
     * 已删除
     */
    private Long coDelete;


}
