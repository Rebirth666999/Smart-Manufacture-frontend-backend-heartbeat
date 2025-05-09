package com.ruoyi.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.bo.IcesExceptionBo;
import com.ruoyi.system.domain.bo.IcesExceptionTaskBo;
import com.ruoyi.system.domain.vo.IcesExceptionTaskVo;
import com.ruoyi.system.domain.vo.IcesExceptionVo;
import com.ruoyi.system.service.IcesExceptionRunningService;
import com.ruoyi.workflow.domain.vo.WfTaskVo;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
public class IcesExceptionRunningController extends BaseController {
    private final IcesExceptionRunningService icesExceptionRunningService;

    /**
     * 查询运行中的流程列表
     */
    @GetMapping("/processList")
    public R<List<IcesExceptionTaskVo>> list() {
        return R.ok(icesExceptionRunningService.selectProcessList());
    }

    /**
     * 查询运行中的流程列表
     */
    @GetMapping("/todoList")
    public R<List<IcesExceptionTaskVo>> todoList() {
        return R.ok(icesExceptionRunningService.selectTodoProcessList());
    }

    /**
     * 查询运行中的流程列表
     */
    @PostMapping("/processXML")
    public R<String> processXML(@RequestBody String defId) {
        return R.ok("查询成功", icesExceptionRunningService.getProcessXML(defId));
    }

    /**
     * 处理任务
     */
    @PostMapping("/handleTask")
    public R<Void> handleTask(@RequestBody IcesExceptionTaskBo task) {
        return toAjax(icesExceptionRunningService.handleTask(task));
    }

}
