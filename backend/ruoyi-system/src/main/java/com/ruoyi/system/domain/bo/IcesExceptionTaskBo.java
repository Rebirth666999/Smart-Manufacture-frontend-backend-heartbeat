package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class IcesExceptionTaskBo extends BaseEntity {
    // 任务ID
    private String taskId;
    // 处理意见
    private String content;
    // 结束流程
    private Boolean end;
}
