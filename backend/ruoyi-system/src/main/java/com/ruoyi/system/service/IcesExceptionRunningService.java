package com.ruoyi.system.service;

import cn.hutool.core.collection.CollUtil;
import com.ruoyi.common.core.service.UserService;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.flowable.core.domain.ProcessQuery;
import com.ruoyi.flowable.factory.FlowServiceFactory;
import com.ruoyi.flowable.utils.ProcessUtils;
import com.ruoyi.system.domain.IcesExceptionLifecycle;
import com.ruoyi.system.domain.IcesExceptionLifecycleVersion;
import com.ruoyi.system.domain.bo.IcesExceptionBo;
import com.ruoyi.system.domain.bo.IcesExceptionLifecycleBo;
import com.ruoyi.system.domain.bo.IcesExceptionLifecycleVersionBo;
import com.ruoyi.system.domain.vo.IcesExceptionLifecycleVersionVo;
import com.ruoyi.system.domain.vo.IcesExceptionLifecycleVo;
import com.ruoyi.system.domain.vo.IcesExceptionTaskVo;
import com.ruoyi.system.domain.vo.IcesExceptionVo;
import lombok.RequiredArgsConstructor;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.repository.Deployment;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 运行中异常处理的service
 *
 * @author YangZY
 * @date 20250509
 */
@RequiredArgsConstructor
@Service
public class IcesExceptionRunningService extends FlowServiceFactory {
    private final UserService userService;
    private final IIcesExceptionService exceptionService;
    private final IIcesExceptionLifecycleService lifecycleService;
    private final IIcesExceptionLifecycleVersionService lifecycleVersionService;

    /**
     * 查找所有流程
     * @return 流程列表
     */
    public List<IcesExceptionTaskVo> selectProcessList() {
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService
            .createHistoricProcessInstanceQuery()
            .orderByProcessInstanceStartTime()
            .desc();
        // 构建搜索条件
        ProcessUtils.buildProcessSearch(historicProcessInstanceQuery, new ProcessQuery());
        List<HistoricProcessInstance> historicProcessInstances = historicProcessInstanceQuery.list();
        List<IcesExceptionTaskVo> taskVoList = new ArrayList<>();
        // 预先找到所有所需列表
        List<IcesExceptionVo> exceptions = exceptionService.queryList(new IcesExceptionBo());
        List<IcesExceptionLifecycleVo> lifecycles = lifecycleService.queryList(new IcesExceptionLifecycleBo());
        List<IcesExceptionLifecycleVersionVo> lifecycleVersions = lifecycleVersionService.queryList(new IcesExceptionLifecycleVersionBo());

        for (HistoricProcessInstance hisIns : historicProcessInstances) {
            IcesExceptionTaskVo taskVo = new IcesExceptionTaskVo();
            taskVo.setCreateTime(hisIns.getStartTime());
            taskVo.setFinishTime(hisIns.getEndTime());
            taskVo.setProcInsId(hisIns.getId());

            // 计算耗时
            if (Objects.nonNull(hisIns.getEndTime())) {
                taskVo.setDuration(DateUtils.getDatePoor(hisIns.getEndTime(), hisIns.getStartTime()));
            } else {
                taskVo.setDuration(DateUtils.getDatePoor(DateUtils.getNowDate(), hisIns.getStartTime()));
            }
            // 流程部署实例信息
            Deployment deployment = repositoryService.createDeploymentQuery()
                .deploymentId(hisIns.getDeploymentId()).singleResult();
            taskVo.setDeployId(hisIns.getDeploymentId());
            taskVo.setProcDefId(hisIns.getProcessDefinitionId());
            taskVo.setProcDefName(hisIns.getProcessDefinitionName());
            taskVo.setProcDefVersion(hisIns.getProcessDefinitionVersion());
            taskVo.setCategory(deployment.getCategory());

            // 找到生命周期版本
            IcesExceptionLifecycleVersionVo currentVersion = lifecycleVersions.stream().filter(lifecycleVersion -> lifecycleVersion.getExlvDefId() != null && lifecycleVersion.getExlvDefId().equals(taskVo.getProcDefId())).findFirst().orElse(null);
            if (currentVersion != null) {
                taskVo.setExlvName(currentVersion.getExlvName());
                // 找到生命周期
                IcesExceptionLifecycleVo currentLifecycle = lifecycles.stream().filter(lifecycle -> lifecycle.getExlCode().equals(currentVersion.getExlCode())).findFirst().orElse(null);
                if (currentLifecycle != null) {
                    // 找到所属异常
                    IcesExceptionVo currentException = exceptions.stream().filter(exception -> exception.getExCode().equals(currentLifecycle.getExCode())).findFirst().orElse(null);
                    if (currentException != null) {
                        taskVo.setExName(currentException.getExName());
                    }
                }
            }
            // 当前所处流程
            List<Task> taskList = taskService.createTaskQuery().processInstanceId(hisIns.getId()).includeIdentityLinks().list();
            if (CollUtil.isNotEmpty(taskList)) {
                taskVo.setTaskName(taskList.stream().map(Task::getName).filter(StringUtils::isNotEmpty).collect(Collectors.joining(",")));
            }
            taskVoList.add(taskVo);
        }
        return taskVoList;
    }
}
