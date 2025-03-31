package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.service.IIcesCodeService;
import com.ruoyi.system.domain.IcesDeviceTaskParam;
import com.ruoyi.system.domain.IcesDeviceTaskPrev;
import com.ruoyi.system.domain.bo.*;
import com.ruoyi.system.domain.vo.IcesDeviceTaskParamVo;
import com.ruoyi.system.domain.vo.IcesDeviceTaskPrevVo;
import com.ruoyi.system.domain.vo.IcesDeviceTaskParamVo;
import com.ruoyi.system.domain.vo.IcesDeviceTaskVo;
import com.ruoyi.system.domain.vo.IcesProcessStepPrevRoundVo;
import com.ruoyi.system.domain.vo.IcesProcessStepPrevVo;
import com.ruoyi.system.domain.vo.IcesProcessStepVo;
import com.ruoyi.system.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.IcesDeviceTaskBo;
import com.ruoyi.system.domain.vo.IcesDeviceTaskVo;
import com.ruoyi.system.domain.IcesDeviceTask;
import com.ruoyi.system.mapper.IcesDeviceTaskMapper;
import com.ruoyi.flowable.factory.FlowServiceFactory;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 设备任务Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-19
 */
@RequiredArgsConstructor
@Service
public class IcesDeviceTaskServiceImpl implements IIcesDeviceTaskService {

    private final IcesDeviceTaskMapper baseMapper;
    private final IIcesCodeService codeService;
    private final IIcesProcessStepService processStepService;
    private final IIcesProcessStepPrevService stepPrevService;
    private final IIcesProcessStepPrevRoundService prevRoundService;
    private final FlowServiceFactory flowServiceFactory;
    private final IIcesDeviceTaskParamService deviceTaskParamService;
    private final IIcesDeviceTaskPrevService deviceTaskPrevService;

    /**
     * 查询设备任务
     */
    @Override
    public IcesDeviceTaskVo queryById(Long dtId){
        return baseMapper.selectVoById(dtId);
    }

    /**
     * 查询设备任务列表
     */
    @Override
    public TableDataInfo<IcesDeviceTaskVo> queryPageList(IcesDeviceTaskBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesDeviceTask> lqw = buildQueryWrapper(bo);
        Page<IcesDeviceTaskVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询设备任务列表
     */
    @Override
    public List<IcesDeviceTaskVo> queryList(IcesDeviceTaskBo bo) {
        LambdaQueryWrapper<IcesDeviceTask> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesDeviceTask> buildQueryWrapper(IcesDeviceTaskBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesDeviceTask> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getDtCode()), IcesDeviceTask::getDtCode, bo.getDtCode());
        lqw.eq(StringUtils.isNotBlank(bo.getEqCode()), IcesDeviceTask::getEqCode, bo.getEqCode());
        lqw.eq(StringUtils.isNotBlank(bo.getMtCode()), IcesDeviceTask::getMtCode, bo.getMtCode());
        lqw.eq(StringUtils.isNotBlank(bo.getEoCode()), IcesDeviceTask::getEoCode, bo.getEoCode());
        lqw.eq(StringUtils.isNotBlank(bo.getDtStat()), IcesDeviceTask::getDtStat, bo.getDtStat());
        lqw.eq(bo.getDtDelete() != null, IcesDeviceTask::getDtDelete, bo.getDtDelete());
        return lqw;
    }

    /**
     * 新增设备任务
     */
    @Override
    public Boolean insertByBo(IcesDeviceTaskBo bo) {
        bo.setDtCode(codeService.insertByType("DeviceTask"));
        IcesDeviceTask add = BeanUtil.toBean(bo, IcesDeviceTask.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setDtId(add.getDtId());
        }
        return flag;
    }

    /**
     * 修改设备任务
     */
    @Override
    public Boolean updateByBo(IcesDeviceTaskBo bo) {
        IcesDeviceTask update = BeanUtil.toBean(bo, IcesDeviceTask.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesDeviceTask entity){
        //TODO 做一些数据校验,如唯一约束


    }

    /**
     * 批量删除设备任务
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }


    @Override
    public void saveDtasks(String jsonStr) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> data = mapper.readValue(jsonStr, Map.class);

        // 获取manufactureTask信息
        Map<String, Object> manufactureTask = (Map<String, Object>) data.get("manufactureTask");
        String mtCode = manufactureTask.get("mtCode").toString();
        Integer mtQtyPlan = Integer.parseInt(manufactureTask.get("mtQtyPlan").toString());

        // 获取deviceTask列表
        List<Map<String, Object>> deviceTasks = (List<Map<String, Object>>) data.get("deviceTask");

        // 创建映射表
        Map<String, String> modelToStepId = new HashMap<>(); // 哈希表a - 修改为String类型
        Map<String, List<String>> stepToPrevSteps = new HashMap<>(); // 哈希表b - 修改为String类型
        Map<String, List<String>> stepToPrevRoundSteps = new HashMap<>(); // 哈希表c - 修改为String类型

        // 填充映射表a

//        这段代码的功能是将设备任务中的模型ID映射到对应的工艺步骤ID，具体逻辑如下：
//        1. 遍历`deviceTasks`列表，提取每个任务的`id`作为`modelId`。
//        2. 调用`processStepService.queryByModel(modelId)`查询与`modelId`对应的工艺步骤对象`step`。
//        3. 如果查询结果不为空，将`modelId`和`step.getPsId()`存入`modelToStepId`哈希表。



        for (Map<String, Object> task : deviceTasks) {
            String modelId = task.get("id").toString();
            IcesProcessStepVo step = processStepService.queryByModel(modelId);
            if (step != null) {
                modelToStepId.put(modelId, step.getPsCode()); // 使用getPsCode()而不是getPsId()
            }
        }

        // 填充映射表b和c
        for (String stepId : modelToStepId.values()) {
            // 获取普通前序步骤
            IcesProcessStepPrevBo icesProcessStepPrevBo = new IcesProcessStepPrevBo();
            icesProcessStepPrevBo.setPsCodeCur(stepId);

            List<IcesProcessStepPrevVo> prevSteps = stepPrevService.queryList(icesProcessStepPrevBo);
            List<String> prevStepIds = prevSteps.stream()
                .map(IcesProcessStepPrevVo::getPsCodePrev) // 使用getPsCodePrev()
                .collect(Collectors.toList());
            stepToPrevSteps.put(stepId, prevStepIds);

            // 获取跨轮次前序步骤
            IcesProcessStepPrevRoundBo icesProcessStepPrevRoundBo = new IcesProcessStepPrevRoundBo();
            icesProcessStepPrevRoundBo.setPsCodeCur(stepId); // 添加这行，设置当前步骤编码
            List<IcesProcessStepPrevRoundVo> prevRoundSteps = prevRoundService.queryList(icesProcessStepPrevRoundBo);
            List<String> prevRoundStepIds = prevRoundSteps.stream()
                .map(IcesProcessStepPrevRoundVo::getPsCodePrev) // 使用getPsCodePrev()
                .collect(Collectors.toList());
            stepToPrevRoundSteps.put(stepId, prevRoundStepIds);
        }

        // 存储每轮的任务ID映射
        Map<String, Long> lastRoundTasks = new HashMap<>();
        Map<String, Long> currentRoundTasks = new HashMap<>();

        // 处理每一轮的任务
        for (int round = 0; round < mtQtyPlan; round++) {
            lastRoundTasks.clear();
            lastRoundTasks.putAll(currentRoundTasks);
            currentRoundTasks.clear();

            for (Map<String, Object> task : deviceTasks) {
                // 创建设备任务
                System.out.println("task = " + task);
                IcesDeviceTask deviceTask = new IcesDeviceTask();
                deviceTask.setEqCode(task.get("eqCode").toString());
//                System.out.println("deviceTask.getEqCode() = " + deviceTask.getEqCode());
                deviceTask.setMtCode(mtCode);
                System .out.println("deviceTask.getMtCode() = " + deviceTask.getMtCode());
                deviceTask.setEoCode(task.get("eoCode").toString());
                System.out.println("deviceTask.getEoCode() = " + deviceTask.getEoCode());
                deviceTask.setDtStat("1"); // 初始状态
                deviceTask.setDtDelete(0L);

                // 插入设备任务并获取ID
                deviceTask.setDtCode(codeService.insertByType("DeviceTask"));
                baseMapper.insert(deviceTask);
                Long dtId = deviceTask.getDtId();
                System.out.println(deviceTask.getDtCode());
                currentRoundTasks.put(task.get("id").toString(), dtId);

                // 处理参数
                List<Map<String, Object>> params = (List<Map<String, Object>>) task.get("param");
                for (Map<String, Object> param : params) {
                    System.out.println("param = " + param);
                    IcesDeviceTaskParamBo taskParamBo = new IcesDeviceTaskParamBo();
                    taskParamBo.setDtCode(deviceTask.getDtCode());
                    taskParamBo.setEospaCode(param.get("eospaCode").toString());
                    System.out.println(taskParamBo.getEospaCode());
                    taskParamBo.setMtCode(mtCode);
//                    taskParamBo.setDtCode(param.get("dtCode").toString());
//                    System.out.println(taskParamBo.getDtCode());
                    taskParamBo.setDtpaValue(param.get("dtpaValue").toString());
                    System.out.println(taskParamBo.getDtpaValue());
                    taskParamBo.setDtpaDelete(0L);
                    deviceTaskParamService.insertByBo(taskParamBo);
                }

                // 处理前序任务关系
                String stepId = modelToStepId.get(task.get("id").toString());

                // 处理同轮次前序任务
                List<String> prevStepIds = stepToPrevSteps.get(stepId);
                if (prevStepIds != null) {
                    for (String prevStepId : prevStepIds) {
                        for (Map<String, Object> prevTask : deviceTasks) {
                            if (modelToStepId.get(prevTask.get("id").toString()).equals(prevStepId)) {
                                Long prevDtId = currentRoundTasks.get(prevTask.get("id").toString());
                                if (prevDtId != null) {
                                    IcesDeviceTaskPrevBo taskPrevBo = new IcesDeviceTaskPrevBo();
                                    taskPrevBo.setMtCode(mtCode);
                                    taskPrevBo.setDtCodeCur(prevTask.get("dtCode").toString());
                                    taskPrevBo.setDtCodePrev(prevTask.get("dtCodePrev").toString());
                                    taskPrevBo.setDtprDelete(0L);
                                    deviceTaskPrevService.insertByBo(taskPrevBo);
                                }
                            }
                        }
                    }
                }

                // 处理跨轮次前序任务
                if (round > 0) {
                    List<String> prevRoundStepIds = stepToPrevRoundSteps.get(stepId);
                    if (prevRoundStepIds != null) {
                        for (String prevStepId : prevRoundStepIds) {
                            for (Map<String, Object> prevTask : deviceTasks) {
                                if (modelToStepId.get(prevTask.get("id").toString()).equals(prevStepId)) {
                                    Long prevDtId = lastRoundTasks.get(prevTask.get("id").toString());
                                    if (prevDtId != null) {
                                        IcesDeviceTaskPrevBo taskPrev = new IcesDeviceTaskPrevBo();
                                        taskPrev.setMtCode(mtCode);
                                        taskPrev.setDtCodeCur(prevTask.get("dtCode").toString());
                                        taskPrev.setDtCodePrev(prevTask.get("dtCodePrev").toString());
                                        taskPrev.setDtprDelete(0L);
                                        deviceTaskPrevService.insertByBo(taskPrev);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


    }
}



