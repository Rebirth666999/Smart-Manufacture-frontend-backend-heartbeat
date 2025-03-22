package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.domain.bo.*;
import com.ruoyi.system.domain.vo.IcesProcessStepPrevRoundVo;
import com.ruoyi.system.domain.vo.IcesProcessStepPrevVo;
import com.ruoyi.system.domain.vo.IcesProcessStepVo;
import com.ruoyi.system.service.*;
import lombok.RequiredArgsConstructor;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.flowable.engine.repository.Model;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.vo.IcesProcessVo;
import com.ruoyi.system.domain.IcesProcess;
import com.ruoyi.system.mapper.IcesProcessMapper;
import com.ruoyi.flowable.factory.FlowServiceFactory;


import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 工艺流程Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-10
 */
@RequiredArgsConstructor
@Service
public class IcesProcessServiceImpl extends FlowServiceFactory implements IIcesProcessService {

    private final IcesProcessMapper baseMapper;
    private final IIcesProcessStepService processStepService;
    private final IIcesProcessStepPrevService stepPrevService;

    private final IIcesProcessStepPrevRoundService prevRoundService;
    private Serializable eoId;

    /**
     * 查询工艺流程
     */
    @Override
    public IcesProcessVo queryById(Long procId) {
        return baseMapper.selectVoById(procId);
    }

    /**
     * 查询工艺流程列表
     */
    @Override
    public TableDataInfo<IcesProcessVo> queryPageList(IcesProcessBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesProcess> lqw = buildQueryWrapper(bo);
        Page<IcesProcessVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询工艺流程列表
     */
    @Override
    public List<IcesProcessVo> queryList(IcesProcessBo bo) {
        LambdaQueryWrapper<IcesProcess> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesProcess> buildQueryWrapper(IcesProcessBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesProcess> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getPrId() != null, IcesProcess::getPrId, bo.getPrId());
        lqw.like(StringUtils.isNotBlank(bo.getProcName()), IcesProcess::getProcName, bo.getProcName());
        lqw.eq(StringUtils.isNotBlank(bo.getProcStat()), IcesProcess::getProcStat, bo.getProcStat());
        lqw.eq(bo.getProcDelete() != null, IcesProcess::getProcDelete, bo.getProcDelete());
        return lqw;
    }

    /**
     * 新增工艺流程
     */
    @Override
    public Boolean insertByBo(IcesProcessBo bo) {
        IcesProcess add = BeanUtil.toBean(bo, IcesProcess.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setProcId(add.getProcId());
        }
        return flag;
    }

    /**
     * 修改工艺流程
     */
    @Override
    public Boolean updateByBo(IcesProcessBo bo) {
        IcesProcess update = BeanUtil.toBean(bo, IcesProcess.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesProcess entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除工艺流程
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
    @Override
    public String queryBpmnXmlById(String modelId) {
        byte[] bpmnBytes = repositoryService.getModelEditorSource(modelId);
        return StrUtil.utf8Str(bpmnBytes);
    }
    @Override
    public void saveProcess(String modelXML) throws DocumentException, JsonProcessingException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new ByteArrayInputStream(modelXML.getBytes(StandardCharsets.UTF_8)));
        Element rootElement = document.getRootElement();
        Element process = rootElement.element("process");  // 定位到process
        String processId = process.attributeValue("id");
        String processName = process.attributeValue("name");
        Long procId = Long.parseLong(processId.replace("process_", ""));
        String modelId = queryById(procId).getProcModel();
        int newModelFlag = 0;  // 是否为新建模型

        // 检查模型是否存在
        Model model = repositoryService.getModel(modelId);
        if (ObjectUtil.isNull(model)) {
            newModelFlag = 1;
            // 新建模型
            Model newModel = repositoryService.newModel();
            newModel.setName(processName);
            newModel.setKey(processId);
            repositoryService.saveModel(newModel);

            // 保存模型ID
            modelId = newModel.getId();
            IcesProcess icesProcess = baseMapper.selectById(procId);
            icesProcess.setProcModel(modelId);
            baseMapper.updateById(icesProcess);
        }

        // 保存XML
        repositoryService.addModelEditorSource(modelId, StringUtils.getBytes(modelXML, StandardCharsets.UTF_8));

        // 不是新模型，先删除原先数据库的所有数据
        if (newModelFlag == 0) {
            // 构造搜索条件
            IcesProcessStepBo bo = new IcesProcessStepBo();
            bo.setProcId(procId);
            // 查找所有符合条件的步骤
            List<IcesProcessStepVo> processStepVos = processStepService.queryList(bo);
            // 取出步骤的ID
            List<Long> processStepIds = new ArrayList<>();
            for (IcesProcessStepVo processStepVo : processStepVos) {
                processStepIds.add(processStepVo.getPsId());
            }
            // 数据库存在步骤需要删除
            if (!processStepIds.isEmpty()) {
                // 用于删除的集合
                List<Long> deleteIds = new ArrayList<>();

                // 找到所有前序步骤关系
                List<IcesProcessStepPrevVo> processStepPrevVos = stepPrevService.queryList(new IcesProcessStepPrevBo());
                // 找到所有与步骤有关的前序关系
                for (IcesProcessStepPrevVo processStepPrevVo : processStepPrevVos) {
                    if (processStepIds.contains(processStepPrevVo.getPsIdCur()) || processStepIds.contains(processStepPrevVo.getPsIdPrev())) {
                        deleteIds.add(processStepPrevVo.getPspId());
                    }
                }
                // 删除前序关系
                if (!deleteIds.isEmpty()) {
                    stepPrevService.deleteWithValidByIds(deleteIds, false);
                    deleteIds.clear();
                }

                // 找到所有跨轮前序步骤关系
                List<IcesProcessStepPrevRoundVo> processStepPrevRoundVos = prevRoundService.queryList(new IcesProcessStepPrevRoundBo());
                // 找到所有与步骤有关的前序关系
                for (IcesProcessStepPrevRoundVo processStepPrevRoundVo : processStepPrevRoundVos) {
                    if (processStepIds.contains(processStepPrevRoundVo.getPsIdCur()) || processStepIds.contains(processStepPrevRoundVo.getPsIdPrev())) {
                        deleteIds.add(processStepPrevRoundVo.getPsprId());
                    }
                }
                // 删除前序关系
                if (!deleteIds.isEmpty()) {
                    prevRoundService.deleteWithValidByIds(deleteIds, false);
                    deleteIds.clear();
                }

                // 最后删除步骤
                processStepService.deleteWithValidByIds(processStepIds, false);
            }
        }

        // 读取XML中的模型
        Iterator<Element> iterator = process.elementIterator();
        List<Element> tasks = new ArrayList<>();  // 所有任务节点
        List<Element> flows = new ArrayList<>();  // 所有连接线
        List<IcesProcessStepBo> steps = new ArrayList<>();  // 步骤实体数组
        Map<String, IcesProcessStepBo> stepMap = new HashMap<>();  // XML中对象id和步骤实体的映射

        while (iterator.hasNext()) {
            Element next = iterator.next();
            // 解析当前节点
            if (Objects.equals(next.getName(), "startEvent")) {
                // 开始事件
                IcesProcessStepBo step = new IcesProcessStepBo();
                step.setProcId(procId);
                step.setPsModel(next.attributeValue("id"));
                steps.add(step);
                stepMap.put(next.attributeValue("id"), step);
            } else if (Objects.equals(next.getName(), "endEvent")) {
                // 结束事件
                IcesProcessStepBo step = new IcesProcessStepBo();
                step.setProcId(procId);
                step.setPsModel(next.attributeValue("id"));
                steps.add(step);
                stepMap.put(next.attributeValue("id"), step);
            } else if (Objects.equals(next.getName(), "serviceTask")) {
                // 操作步骤
                tasks.add(next);
                IcesProcessStepBo step = new IcesProcessStepBo();
                step.setProcId(procId);
                step.setPsModel(next.attributeValue("id"));
                step.setMoId(Long.parseLong(next.attributeValue("moId")));
                step.setPsDesc(next.attributeValue("psDesc"));
                steps.add(step);
                stepMap.put(next.attributeValue("id"), step);
            } else if (Objects.equals(next.getName(), "sequenceFlow")) {
                flows.add(next);
            }
        }

        // 向数据库插入steps中所有对象
        for (IcesProcessStepBo step : steps) {
            processStepService.insertByBo(step);
        }


        // 先读出prev属性，有的话解析json成字符串数组，for 遍历把数组id用map 映射process实体。
        // 取出实体中的psID,new一个跨轮前序关联实体bo，把对应的两个id放进去，用service的方法插入

        // 解析flows，保存前序任务关系
        for (Element flow : flows) {
            IcesProcessStepPrevBo stepPrevBo = new IcesProcessStepPrevBo();
            if (stepMap.get(flow.attributeValue("targetRef")) != null)
                stepPrevBo.setPsIdCur(stepMap.get(flow.attributeValue("targetRef")).getPsId());
            if (stepMap.get(flow.attributeValue("sourceRef")) != null)
                stepPrevBo.setPsIdPrev(stepMap.get(flow.attributeValue("sourceRef")).getPsId());
            // 仅在两个ID都存在时插入
            if (stepPrevBo.getPsIdCur() != null && stepPrevBo.getPsIdPrev() != null) {
                stepPrevService.insertByBo(stepPrevBo);
            }
        }

        // 处理prev属性
        for (Element task : tasks) {
            String prevJson = task.attributeValue("prev"); // 获取prev属性
            if (StringUtils.isNotBlank(prevJson)) {
                // 解析JSON成字符串数组
                String[] prevIds = new ObjectMapper().readValue(prevJson, String[].class);
                for (String prevId : prevIds) {
                    IcesProcessStepBo currentStep = stepMap.get(task.attributeValue("id"));
                    IcesProcessStepBo prevStep = stepMap.get(prevId);
                    if (currentStep != null && prevStep != null) {
                        IcesProcessStepPrevRoundBo stepPrevRoundBo = new IcesProcessStepPrevRoundBo();
                        stepPrevRoundBo.setPsIdCur(currentStep.getPsId());
                        stepPrevRoundBo.setPsIdPrev(prevStep.getPsId());
                        prevRoundService.insertByBo(stepPrevRoundBo);
                    }
                }
            }
        }

    }
}
