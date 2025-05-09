package com.ruoyi.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.bo.IcesExceptionBo;
import com.ruoyi.system.domain.vo.IcesExceptionTaskVo;
import com.ruoyi.system.domain.vo.IcesExceptionVo;
import com.ruoyi.system.service.IcesExceptionRunningService;
import com.ruoyi.workflow.domain.vo.WfTaskVo;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 异常运行相关接口
 *
 * @author YangZY
 * @date 20250509
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/exception/running")
public class IcesExceptionRunningController {
    private final IcesExceptionRunningService icesExceptionRunningService;

    /**
     * 查询运行中的流程列表
     */
    @GetMapping("/processList")
    public List<IcesExceptionTaskVo> list() {
        return icesExceptionRunningService.selectProcessList();
    }

}
