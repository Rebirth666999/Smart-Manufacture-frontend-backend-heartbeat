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
        Long mtId = Long.parseLong(manufactureTask.get("mtId").toString());
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

//        这段代码的主要功能是为每个步骤（step）获取其普通前序步骤和跨轮次前序步骤，并将结果存储到两个映射表中。以下是代码的详细逻辑分解：
//        遍历 modelToStepId 的值集合：
//        modelToStepId 是一个映射表，键为模型 ID（modelId），值为对应的步骤 ID（stepId）。
//        遍历 modelToStepId.values()，即对每个步骤 ID 进行处理。
//        获取普通前序步骤：
//        创建一个 IcesProcessStepPrevBo 对象，作为查询条件。
//        调用 stepPrevService.queryList(icesProcessStepPrevBo) 方法，获取与当前步骤相关的普通前序步骤列表（prevSteps）。
//        使用 Java Stream API 将 prevSteps 中的每个对象的 PsIdPrev 属性提取出来，形成一个 Long 类型的列表（prevStepIds）。
//        将当前步骤 ID（stepId）与对应的普通前序步骤 ID 列表（prevStepIds）存入映射表 stepToPrevSteps。
//        获取跨轮次前序步骤：
//        创建一个 IcesProcessStepPrevRoundBo 对象，作为查询条件。
//        调用 prevRoundService.queryList(icesProcessStepPrevRoundBo) 方法，获取与当前步骤相关的跨轮次前序步骤列表（prevRoundSteps）。
//        同样使用 Java Stream API 提取 prevRoundSteps 中的每个对象的 PsIdPrev 属性，形成一个 Long 类型的列表（prevRoundStepIds）。
//        将当前步骤 ID（stepId）与对应的跨轮次前序步骤 ID 列表（prevRoundStepIds）存入映射表 stepToPrevRoundSteps。
//        存储结果：
//        普通前序步骤的结果存储在 stepToPrevSteps 映射表中，键为步骤 ID，值为普通前序步骤 ID 列表。
//        跨轮次前序步骤的结果存储在 stepToPrevRoundSteps 映射表中，键为步骤 ID，值为跨轮次前序步骤 ID 列表。
        // 填充映射表b和c
        for (String stepId : modelToStepId.values()) {
            // 获取普通前序步骤
            IcesProcessStepPrevBo icesProcessStepPrevBo = new IcesProcessStepPrevBo();
//            这段代码的功能是从prevSteps列表中提取每个步骤的前序步骤ID（PsIdPrev），并将其收集到一个List<Long>类型的列表中。具体逻辑如下：
//            使用stream()方法对prevSteps列表进行流式处理。
//            调用map(IcesProcessStepPrevVo::getPsIdPrev)提取每个IcesProcessStepPrevVo对象的PsIdPrev属性。
//            使用collect(Collectors.toList())将提取的结果收集为一个List<Long>。
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
//        外层循环：根据生产计划数量（mtQtyPlan）执行多轮任务创建，每轮处理一组设备任务。
//        任务映射更新：
//        将当前轮次的任务映射（currentRoundTasks）保存到上一轮任务映射（lastRoundTasks）中。
//        清空当前轮次的任务映射，为本轮任务的重新填充做准备。
//        遍历设备任务列表：
//        遍历deviceTasks列表，每个任务表示一个设备任务。
//        创建设备任务对象：
//        创建IcesDeviceTask对象，并设置以下属性：
//        eqId：从任务数据中提取设备ID。
//        mtId：从生产任务信息中获取生产任务ID。
//        eoId：从任务数据中提取操作ID。
//        dtStat：设置初始状态为"1"。
//        dtDelete：设置删除标志为0，表示未删除。
//        插入设备任务：
//        调用baseMapper.insert(deviceTask)将设备任务插入数据库。
//        获取插入后生成的任务ID（dtId），并将其与任务ID（task.get("id")）存入当前轮次的任务映射（currentRoundTasks）。
//        处理任务参数：
//        遍历任务参数列表（params），为每个参数创建IcesDeviceTaskParamBo对象。
//        设置参数对象的属性：
//        eospaId：从参数数据中提取操作步骤参数ID。
//        dtId：设置为当前设备任务的ID。
//        dtpaValue：从参数数据中提取参数值。
//        dtpaDelete：设置删除标志为0。
//        调用deviceTaskParamService.insertByBo(taskParamBo)将参数插入服务
        for (int round = 0; round < mtQtyPlan; round++) {
            lastRoundTasks.clear();
            lastRoundTasks.putAll(currentRoundTasks);
            currentRoundTasks.clear();

            for (Map<String, Object> task : deviceTasks) {
                // 创建设备任务
                IcesDeviceTask deviceTask = new IcesDeviceTask();
                deviceTask.setEqCode(task.get("eqCode").toString());
                deviceTask.setMtCode(task.get("mtCode").toString());
                deviceTask.setEoCode(task.get("eoCode").toString());
                deviceTask.setDtStat("1"); // 初始状态
                deviceTask.setDtDelete(0L);

                // 插入设备任务并获取ID
                baseMapper.insert(deviceTask);
                Long dtId = deviceTask.getDtId();
                currentRoundTasks.put(task.get("id").toString(), dtId);

                // 处理参数
                List<Map<String, Object>> params = (List<Map<String, Object>>) task.get("param");
                for (Map<String, Object> param : params) {
                    IcesDeviceTaskParamBo taskParamBo = new IcesDeviceTaskParamBo();
                    taskParamBo.setEospaCode(param.get("eospaCode").toString());
                    taskParamBo.setDtCode(param.get("dtCode").toString());
                    taskParamBo.setDtpaValue(param.get("dtpaValue").toString());
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
                                    taskPrevBo .setDtCodeCur(prevTask.get("dtCode").toString());
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



