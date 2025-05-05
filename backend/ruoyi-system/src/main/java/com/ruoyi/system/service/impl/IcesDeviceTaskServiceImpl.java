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
import com.ruoyi.system.domain.vo.*;
import com.ruoyi.system.service.IIcesCodeService;
import com.ruoyi.system.domain.bo.*;
import com.ruoyi.system.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.IcesDeviceTaskBo;
import com.ruoyi.system.domain.IcesDeviceTask;
import com.ruoyi.system.mapper.IcesDeviceTaskMapper;

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
    private final IIcesDeviceTaskParamService deviceTaskParamService;
    private final IIcesDeviceTaskPrevService deviceTaskPrevService;
    private final IIcesManufactureTaskService manufactureTaskService;

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

        // bo是否已完成
        if(Objects.equals(bo.getDtStat(), "4")){
            IcesDeviceTaskBo icesDeviceTaskBo = new IcesDeviceTaskBo();
            icesDeviceTaskBo.setMtCode(bo.getMtCode());
            List<IcesDeviceTaskVo> otherBos=queryList(bo);
            int done=1;
            for(IcesDeviceTaskVo vo:otherBos){
                if(!vo.getMtCode().equals("4")){
                    done=0;
                    break;
                }
            }
            if(done==1){
                System.out.println("任务已经全部完成");
                manufactureTaskService.updateStatus(bo);
            }
        }
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

    /**
     * 保存设备任务
     * @param jsonStr 前端传来的JSON格式数据
     */
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
        Map<String, IcesProcessStepVo> modelIdToStep = new HashMap<>();  // 工艺步骤的modelId映射到对象
        Map<IcesProcessStepVo, List<String>> stepToPrevSteps = new HashMap<>();  // 工艺步骤前序步骤映射表，映射到code
        Map<IcesProcessStepVo, List<String>> stepToPrevRoundSteps = new HashMap<>();  // 工艺步骤跨轮次前序步骤映射表，映射到code

        // 查找所属的工艺步骤
        for (Map<String, Object> task : deviceTasks) {
            String modelId = task.get("id").toString();
            IcesProcessStepVo step = processStepService.queryByModel(modelId);
            if (step != null) {
                modelIdToStep.put(modelId, step);
            }
        }

        // 删除数据库已有的内容
        deleteExisting(mtCode);

        // 查找前序步骤和跨轮前序步骤
        // 遍历所有的工艺步骤
        for (IcesProcessStepVo step : modelIdToStep.values()) {
            // 获取前序步骤
            IcesProcessStepPrevBo icesProcessStepPrevBo = new IcesProcessStepPrevBo();
            icesProcessStepPrevBo.setPsCodeCur(step.getPsCode());
            List<IcesProcessStepPrevVo> prevSteps = stepPrevService.queryList(icesProcessStepPrevBo);
            List<String> prevStepCodes = prevSteps.stream()
                .map(IcesProcessStepPrevVo::getPsCodePrev)
                .collect(Collectors.toList());
            stepToPrevSteps.put(step, prevStepCodes);

            // 获取跨轮次前序步骤
            IcesProcessStepPrevRoundBo icesProcessStepPrevRoundBo = new IcesProcessStepPrevRoundBo();
            icesProcessStepPrevRoundBo.setPsCodeCur(step.getPsCode()); // 添加这行，设置当前步骤编码
            List<IcesProcessStepPrevRoundVo> prevRoundSteps = prevRoundService.queryList(icesProcessStepPrevRoundBo);
            List<String> prevRoundStepCodes = prevRoundSteps.stream()
                .map(IcesProcessStepPrevRoundVo::getPsCodePrev)
                .collect(Collectors.toList());
            stepToPrevRoundSteps.put(step, prevRoundStepCodes);
        }

        // 存储每轮的任务映射
        // 对应工艺步骤modelID映射到deviceTask对象
        Map<String, IcesDeviceTask> lastRoundTasks = new HashMap<>();
        Map<String, IcesDeviceTask> currentRoundTasks = new HashMap<>();

        // 处理每一轮的任务
        for (int round = 0; round < mtQtyPlan; round++) {
            // 当前轮次转为前一轮次
            lastRoundTasks.clear();
            lastRoundTasks.putAll(currentRoundTasks);
            currentRoundTasks.clear();

            for (Map<String, Object> task : deviceTasks) {
                // 创建设备任务
                IcesDeviceTask deviceTask = new IcesDeviceTask();
                deviceTask.setMtCode(mtCode);
                deviceTask.setEqCode(task.get("eqCode").toString());
                deviceTask.setEoCode(task.get("eoCode").toString());
                deviceTask.setDtModel(task.get("dtModel").toString());
                deviceTask.setDtStat("1");
                deviceTask.setDtDelete(0L);
                deviceTask.setDtCode(codeService.insertByType("DeviceTask"));
                baseMapper.insert(deviceTask);
                currentRoundTasks.put(task.get("id").toString(), deviceTask);

                // 处理参数
                List<Map<String, Object>> params = (List<Map<String, Object>>) task.get("param");
                for (Map<String, Object> param : params) {
                    IcesDeviceTaskParamBo taskParamBo = new IcesDeviceTaskParamBo();
                    taskParamBo.setDtCode(deviceTask.getDtCode());
                    taskParamBo.setMtCode(mtCode);
                    taskParamBo.setEospaCode(param.get("eospaCode").toString());
                    taskParamBo.setDtpaValue(param.get("dtpaValue").toString());
                    taskParamBo.setDtpaDelete(0L);
                    deviceTaskParamService.insertByBo(taskParamBo);
                }}
            for (Map<String, Object> task : deviceTasks){
                // 找到当前设备任务对应的工艺步骤
                IcesProcessStepVo step = modelIdToStep.get(task.get("id").toString());
                IcesDeviceTask deviceTask = currentRoundTasks.get(task.get("id").toString());
                // 处理同轮次前序任务
                List<String> prevStepCodes = stepToPrevSteps.get(step);
                if (prevStepCodes != null && !prevStepCodes.isEmpty()) {
                    for (String prevStepCode : prevStepCodes) {
                        // 遍历所有的前序步骤
                        // prevStepId是工艺步骤的code
                        for (Map<String, Object> prevTask : deviceTasks) {
                            // 遍历所有传入的设备任务
                            if (modelIdToStep.get(prevTask.get("id").toString()).getPsCode().equals(prevStepCode)) {
                                // 找到在list中的的设备任务
                                IcesDeviceTask deviceTaskPrev = currentRoundTasks.get(prevTask.get("id").toString());
                                if (deviceTaskPrev != null) {
                                    IcesDeviceTaskPrevBo taskPrevBo = new IcesDeviceTaskPrevBo();
                                    taskPrevBo.setMtCode(mtCode);
                                    taskPrevBo.setDtCodeCur(deviceTask.getDtCode());  // 当前设备任务
                                    taskPrevBo.setDtCodePrev(deviceTaskPrev.getDtCode());  // 前序设备任务
                                    taskPrevBo.setDtprDelete(0L);
                                    deviceTaskPrevService.insertByBo(taskPrevBo);
                                }
                            }
                        }
                    }
                }

                // 处理跨轮次前序任务
                if (round > 0) {
                    List<String> prevRoundStepCodes = stepToPrevRoundSteps.get(step);
                    if (prevRoundStepCodes != null && !prevRoundStepCodes.isEmpty()) {
                        for (String prevStepCode : prevRoundStepCodes) {
                            for (Map<String, Object> prevTask : deviceTasks) {
                                if (modelIdToStep.get(prevTask.get("id").toString()).getPsCode().equals(prevStepCode)) {
                                    IcesDeviceTask deviceTaskPrev = lastRoundTasks.get(prevTask.get("id").toString());
                                    if (deviceTaskPrev != null) {
                                        IcesDeviceTaskPrevBo taskPrevBo = new IcesDeviceTaskPrevBo();
                                        taskPrevBo.setMtCode(mtCode);
                                        taskPrevBo.setDtCodeCur(deviceTask.getDtCode());
                                        taskPrevBo.setDtCodePrev(deviceTaskPrev.getDtCode());
                                        taskPrevBo.setDtprDelete(0L);
                                        deviceTaskPrevService.insertByBo(taskPrevBo);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 删除数据库已有的与当前生产任务关联的
     * 设备任务、任务前序关系、任务参数
     * @param mtCode 生产任务
     */
    private void deleteExisting(String mtCode) {
        // 如果数据库中已经存在步骤参数信息，则删除
        IcesDeviceTaskParamBo taskParamBo = new IcesDeviceTaskParamBo();
        taskParamBo.setMtCode(mtCode);
        List<IcesDeviceTaskParamVo> taskParamVos = deviceTaskParamService.queryList(taskParamBo);
        List<Long> taskParamIds = new ArrayList<>();
        for (IcesDeviceTaskParamVo taskParamVo : taskParamVos) {
            taskParamIds.add(taskParamVo.getDtpaId());
        }
        if (!taskParamIds.isEmpty()) {
            deviceTaskParamService.deleteWithValidByIds(taskParamIds, false);
        }

        // 如果数据库中已经存在前序步骤信息，则删除
        IcesDeviceTaskPrevBo taskPrevBo = new IcesDeviceTaskPrevBo();
        taskPrevBo.setMtCode(mtCode);
        List<IcesDeviceTaskPrevVo> taskPrevVos = deviceTaskPrevService.queryList(taskPrevBo);
        List<Long> taskPrevIds = new ArrayList<>();
        for (IcesDeviceTaskPrevVo taskPrevVo : taskPrevVos) {
            taskPrevIds.add(taskPrevVo.getDtprId());
        }
        if (!taskPrevIds.isEmpty()) {
            deviceTaskPrevService.deleteWithValidByIds(taskPrevIds, false);
        }

        // 如果数据库中已经存在步骤信息，则删除
        IcesDeviceTaskBo taskBo = new IcesDeviceTaskBo();
        taskBo.setMtCode(mtCode);
        List<IcesDeviceTaskVo> taskVos = queryList(taskBo);
        List<Long> taskIds = new ArrayList<>();
        for (IcesDeviceTaskVo taskVo : taskVos) {
            taskIds.add(taskVo.getDtId());
        }
        if (!taskIds.isEmpty()) {
            deleteWithValidByIds(taskIds, false);
        }
    }

    /**
     * 找到当前生产任务当前轮次还需要执行的设备任务
     * @param mtCode 生产任务
     * @return 当前轮次还需要执行的设备任务
     * @author YangZY
     * @date 20250504
     */
    @Override
    public List<IcesDeviceTaskVo> findRemain(String mtCode) {
        // 找到生产任务，应当只有一个
        IcesManufactureTaskBo manufactureTaskBo = new IcesManufactureTaskBo();
        manufactureTaskBo.setMtCode(mtCode);
        List<IcesManufactureTaskVo> manufactureTaskVos = manufactureTaskService.queryList(manufactureTaskBo);
        assert manufactureTaskVos.size() == 1;
        // 找到所有工艺步骤
        IcesProcessStepBo processStepBo = new IcesProcessStepBo();
        processStepBo.setProcCode(manufactureTaskVos.get(0).getProcCode());
        List<IcesProcessStepVo> processStepVos = processStepService.queryList(processStepBo);

        // 找到开始事件和结束事件
        // 创建code到processStep的映射
        // 此处start和end等价
        IcesProcessStepVo start = null;
        IcesProcessStepVo end = null;
        Map<String, IcesProcessStepVo> processStepMap = new HashMap<>();
        for (IcesProcessStepVo processStepVo : processStepVos) {
            // 寻找没有对应模型操作的步骤
            if (processStepVo.getMoCode() == null) {
                // 第一个给start
                // 第二个给end
                if (start == null) {
                    start = processStepVo;
                } else {
                    end = processStepVo;
                }
            }
            // 关系加入映射
            processStepMap.put(processStepVo.getPsCode(), processStepVo);
        }
        // 工艺流程应当存在开始事件和结束事件
        assert start != null;
        assert end != null;

        // 找到工艺步骤的所有前序关系
        IcesProcessStepPrevBo stepPrevBo = new IcesProcessStepPrevBo();
        stepPrevBo.setProcCode(manufactureTaskVos.get(0).getProcCode());
        List<IcesProcessStepPrevVo> stepPrevVos = stepPrevService.queryList(stepPrevBo);
        // 找到起始操作和结束操作
        // 取出model字符串
        List<String> startSteps = new ArrayList<>();
        List<String> endSteps = new ArrayList<>();
        for (IcesProcessStepPrevVo stepPrevVo : stepPrevVos) {
            // 起始操作
            // 前序是开始事件
            if (stepPrevVo.getPsCodePrev().equals(start.getPsCode()) ||
                stepPrevVo.getPsCodePrev().equals(end.getPsCode())) {
                startSteps.add(processStepMap.get(stepPrevVo.getPsCodeCur()).getPsModel());
            }
            // 结束操作
            // 是结束事件的前序
            if (stepPrevVo.getPsCodeCur().equals(start.getPsCode()) ||
                stepPrevVo.getPsCodeCur().equals(end.getPsCode())) {
                endSteps.add(processStepMap.get(stepPrevVo.getPsCodePrev()).getPsModel());
            }
        }
        // 找到所有设备任务
        IcesDeviceTaskBo bo = new IcesDeviceTaskBo();
        bo.setMtCode(mtCode);
        List<IcesDeviceTaskVo> tasks = queryList(bo);
        // 找到起始任务和结束任务
        List<IcesDeviceTaskVo> allTasks = new ArrayList<>();         // 本轮次所有任务
        List<IcesDeviceTaskVo> startTasks = new ArrayList<>();       // 本轮次所有起始任务
        List<IcesDeviceTaskVo> endTasks = new ArrayList<>();         // 本轮次所有结束任务
        List<IcesDeviceTaskVo> remainingTasks = new ArrayList<>();   // 要返回的所有剩余任务
        int ssCnt = 0;  // 起始任务的已开始数量
        int eeCnt = 0;  // 结束任务的已结束数量
        for (IcesDeviceTaskVo task : tasks) {
            // 添加任务
            allTasks.add(task);
            // 起始任务
            if (startSteps.contains(task.getDtModel())) {
                startTasks.add(task);
                if (task.getDtStat().equals("4") || task.getDtStat().equals("3")) {
                    // 已完成、进行中
                    ssCnt++;
                }
            }
            // 结束任务
            if (endSteps.contains(task.getDtModel())) {
                endTasks.add(task);
                if (task.getDtStat().equals("4")) {
                    // 已完成
                    eeCnt++;
                }
            }
            // 找全了一轮次的起始和结束任务，判断当前轮次状态
            // 所有的起始任务未开始，则轮次未开始
            // 有一个起始任务已开始，则轮次已开始
            // 全部的结束任务都结束，则轮次已结束
            if (startTasks.size() == startSteps.size() && endTasks.size() == endSteps.size()) {
                if (ssCnt == 0 || eeCnt == endSteps.size()) {
                    // 本轮未开始、本轮已结束
                    // 不需要执行任何操作
                    ssCnt = 0;
                    eeCnt = 0;
                    allTasks.clear();
                    startTasks.clear();
                    endTasks.clear();
                } else {
                    // 本轮已开始，但未结束
                    // 找到未开始的任务
                    for (IcesDeviceTaskVo taskVo : allTasks) {
                        if (task.getDtStat().equals("2")) {
                            // 已下发，未开始
                            remainingTasks.add(taskVo);
                        }
                    }
                    // 本轮处理完成
                    ssCnt = 0;
                    eeCnt = 0;
                    allTasks.clear();
                    startTasks.clear();
                    endTasks.clear();
                }
            }
        }
        return remainingTasks;
    }

    /**
     * 找到指定车间所有本轮还需执行的设备任务
     * @param arCode 车间编号
     * @return 还需要执行的设备任务
     * @author YangZY
     * @date 20250504
     */
    @Override
    public List<IcesDeviceTaskVo> findAllRemain(String arCode) {
        // 找到此车间的所有生产任务
        IcesManufactureTaskBo manufactureTaskBo = new IcesManufactureTaskBo();
        manufactureTaskBo.setArCode(arCode);
        List<IcesManufactureTaskVo> manufactureTaskVos = manufactureTaskService.queryList(manufactureTaskBo);
        // 遍历所有的生产任务
        List<IcesDeviceTaskVo> result = new ArrayList<>();
        for (IcesManufactureTaskVo manufactureTaskVo : manufactureTaskVos) {
            // 只有未完成的生产任务会被计算
            if (!manufactureTaskVo.getMtStat().equals("6")) {
                result.addAll(findRemain(manufactureTaskVo.getMtCode()));
            }
        }
        return result;
    }
}



