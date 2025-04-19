package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.domain.bo.*;
import com.ruoyi.system.domain.vo.*;
import com.ruoyi.system.service.*;
import lombok.RequiredArgsConstructor;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.flowable.engine.repository.Model;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.IcesProcess;
import com.ruoyi.system.mapper.IcesProcessMapper;
import com.ruoyi.flowable.factory.FlowServiceFactory;
import org.springframework.transaction.annotation.Transactional;


import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
    private final IIcesCodeService codeService;
    private final IIcesProcessStepService processStepService;
    private final IIcesProcessStepPrevService stepPrevService;
    private final IIcesProcessStepPrevRoundService prevRoundService;
    private final IIcesProcessMaterialService processMaterialService;

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

    @Override
    public TableDataInfo<IcesProcessVo> queryReviewList(IcesProcessBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesProcess> lqw = buildQueryWrapper(bo);
        List<String> stats = new ArrayList<>();
        stats.add("2");  // 待审核
        stats.add("3");  // 审核中
        stats.add("7");  // 待审核（弃用）
        stats.add("8");  // 审核中（弃用）
        lqw.in(IcesProcess::getProcStat, stats);
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
        lqw.eq(StringUtils.isNotBlank(bo.getProcCode()), IcesProcess::getProcCode, bo.getProcCode());
        lqw.eq(StringUtils.isNotBlank(bo.getPrCode()), IcesProcess::getPrCode, bo.getPrCode());
        lqw.like(StringUtils.isNotBlank(bo.getProcName()), IcesProcess::getProcName, bo.getProcName());
        lqw.eq(StringUtils.isNotBlank(bo.getProcStat()), IcesProcess::getProcStat, bo.getProcStat());
        lqw.eq(bo.getProcDelete() != null, IcesProcess::getProcDelete, bo.getProcDelete());
        return lqw;
    }

    /**
     * 新增工艺流程
     */
    @Override
    public IcesProcessVo insertByBo(IcesProcessBo bo) {
        bo.setProcCode(codeService.insertByType("Process"));
        String cMan = getLoginUsername();
        String cDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        // 填入创建信息
        bo.setProcCman(cMan);
        bo.setProcCdate(cDate);
        IcesProcess add = BeanUtil.toBean(bo, IcesProcess.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setProcId(add.getProcId());
        }
        return queryById(add.getProcId());
    }

    /**
     * 修改工艺流程
     */
    @Override
    public Boolean updateByBo(IcesProcessBo bo) {
        String mMan = getLoginUsername();
        String mDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        // 获取原先记录
        IcesProcessVo vo = queryById(bo.getProcId());
        // 如果原先未激活，现在已激活，则填入发布信息
        if (bo.getProcStat() != null && vo.getProcStat().equals("4") && bo.getProcStat().equals("5")) {
            bo.setProcRman(mMan);
            bo.setProcRdate(mDate);
        }
        // 填入修改信息
        bo.setProcMman(mMan);
        bo.setProcMdate(mDate);
        IcesProcess update = BeanUtil.toBean(bo, IcesProcess.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 获取当前用户名称
     * @return 用户名
     */
    private String getLoginUsername() {
        LoginUser loginUser;
        try {
            loginUser = LoginHelper.getLoginUser();
        } catch (Exception e) {
            return null;
        }
        return ObjectUtil.isNotNull(loginUser) ? loginUser.getUsername() : null;
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
    /**
     * 保存流程模型（增量更新版本）
     * @param modelXML 流程模型的XML字符串
     * @throws DocumentException XML解析异常
     * @throws JsonProcessingException JSON处理异常
     */
    @Transactional
    public void saveProcess(String modelXML) throws DocumentException, JsonProcessingException {
        // ========== 1. 解析XML和基本模型处理 ==========

        // 使用SAXReader解析XML字符串
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new ByteArrayInputStream(modelXML.getBytes(StandardCharsets.UTF_8)));

        // 获取XML根元素和process元素
        Element rootElement = document.getRootElement();
        Element process = rootElement.element("process");  // 定位到process节点

        // 从process元素中获取流程ID和名称
        String processId = process.attributeValue("id");
        String processName = process.attributeValue("name");

        // 从流程ID中提取数字部分作为procId
        Long procId = Long.parseLong(processId.replace("process_", ""));

        // 查询数据库获取流程信息
        IcesProcess icesProcess = baseMapper.selectById(procId);
        String procModel = icesProcess.getProcModel();  // 流程模型ID
        String procCode = icesProcess.getProcCode();    // 流程编码
        int newModelFlag = 0;  // 标记是否为新建模型(0:已有模型,1:新建模型)

        // 检查模型是否已存在
        Model model = repositoryService.getModel(procModel);
        if (ObjectUtil.isNull(model)) {
            newModelFlag = 1;
            // 新建模型并保存
            Model newModel = repositoryService.newModel();
            newModel.setName(processName);   // 设置模型名称
            newModel.setKey(processId);      // 设置模型Key
            repositoryService.saveModel(newModel);

            // 更新流程的模型ID
            procModel = newModel.getId();
            icesProcess.setProcModel(procModel);
            baseMapper.updateById(icesProcess);
        }

        // 保存XML到模型库
        repositoryService.addModelEditorSource(procModel, StringUtils.getBytes(modelXML, StandardCharsets.UTF_8));

        // ========== 2. 获取现有数据快照 ==========

        // 用于存储现有数据的Map:
        // key: 业务标识, value: 数据库实体

        // 现有步骤映射(psModel -> 步骤实体)
        Map<String, IcesProcessStepVo> existingSteps = new HashMap<>();
        // 现有普通流程关系映射(源步骤编码->目标步骤编码 -> 关系实体)
        Map<String, IcesProcessStepPrevVo> existingFlows = new HashMap<>();
        // 现有跨轮次流程关系映射(源步骤编码->目标步骤编码 -> 关系实体)
        Map<String, IcesProcessStepPrevRoundVo> existingRoundFlows = new HashMap<>();
        // 现有原料需求关系映射(maCode -> 关系实体)
        Map<String, IcesProcessMaterialVo> existingProcessMaterials = new HashMap<>();

        // 如果不是新建模型，需要获取现有数据用于增量比较
        if (newModelFlag == 0) {
            // 2.1 查询并缓存现有步骤
            IcesProcessStepBo processStepBo = new IcesProcessStepBo();
            processStepBo.setProcCode(procCode);
            List<IcesProcessStepVo> stepList = processStepService.queryList(processStepBo);
            // 以psModel(模型中的元素ID)为key缓存步骤
            stepList.forEach(step -> existingSteps.put(step.getPsModel(), step));

            // 2.2 查询并缓存现有普通流程关系
            IcesProcessStepPrevBo stepPrevBo = new IcesProcessStepPrevBo();
            stepPrevBo.setProcCode(procCode);
            List<IcesProcessStepPrevVo> flowList = stepPrevService.queryList(stepPrevBo);
            // 以"源步骤编码->目标步骤编码"为key缓存关系
            flowList.forEach(flow -> existingFlows.put(flow.getPsCodePrev() + "->" + flow.getPsCodeCur(), flow));

            // 2.3 查询并缓存现有跨轮次流程关系
            IcesProcessStepPrevRoundBo prevRoundBo = new IcesProcessStepPrevRoundBo();
            prevRoundBo.setProcCode(procCode);
            List<IcesProcessStepPrevRoundVo> roundFlowList = prevRoundService.queryList(prevRoundBo);
            // 以"源步骤编码->目标步骤编码"为key缓存关系
            roundFlowList.forEach(flow -> existingRoundFlows.put(flow.getPsCodePrev() + "->" + flow.getPsCodeCur(), flow));

            // 2.4 查询并缓存现有的原料需求关系
            IcesProcessMaterialBo processMaterialBo = new IcesProcessMaterialBo();
            processMaterialBo.setProcCode(procCode);
            List<IcesProcessMaterialVo> processMaterialList = processMaterialService.queryList(processMaterialBo);
            // 以maCode(原料种类)为key缓存步骤
            processMaterialList.forEach(processMaterial -> existingProcessMaterials.put(processMaterial.getMaCode(), processMaterial));
        }

        // ========== 3. 解析XML中的节点 ==========

        // 用于存储从XML解析出的数据
        List<Element> tasks = new ArrayList<>();    // 所有任务节点
        List<Element> flows = new ArrayList<>();    // 所有连接线
        List<IcesProcessStepBo> steps = new ArrayList<>();  // 所有步骤业务对象
        Map<String, IcesProcessStepBo> stepMap = new HashMap<>();  // XML元素ID到步骤业务的映射

        // 遍历process元素的所有子元素
        Iterator<Element> iterator = process.elementIterator();
        while (iterator.hasNext()) {
            Element next = iterator.next();
            String elementName = next.getName();

            // 3.1 处理开始/结束事件
            if (Objects.equals(elementName, "startEvent") || Objects.equals(elementName, "endEvent")) {
                IcesProcessStepBo step = new IcesProcessStepBo();
                step.setProcCode(procCode);
                step.setPsModel(next.attributeValue("id"));  // 使用XML元素ID作为模型ID
                steps.add(step);
                stepMap.put(next.attributeValue("id"), step);
            }
            // 3.2 处理服务任务
            else if (Objects.equals(elementName, "serviceTask")) {
                tasks.add(next);  // 添加到任务列表
                IcesProcessStepBo step = new IcesProcessStepBo();
                step.setProcCode(procCode);
                step.setPsModel(next.attributeValue("id"));
                step.setMoCode(next.attributeValue("moCode"));  // 模型对象编码
                step.setPsDesc(next.attributeValue("psDesc")); // 步骤描述
                steps.add(step);
                stepMap.put(next.attributeValue("id"), step);
            }
            // 3.3 处理连接线
            else if (Objects.equals(elementName, "sequenceFlow")) {
                flows.add(next);
            }
        }

        // ========== 4. 处理步骤 - 增量更新 ==========

        for (IcesProcessStepBo newStep : steps) {
            String psModel = newStep.getPsModel();

            // 4.1 如果步骤已存在，则更新
            if (existingSteps.containsKey(psModel)) {
                // 获取已有步骤的ID
                IcesProcessStepVo existing = existingSteps.get(psModel);
                newStep.setPsId(existing.getPsId());  // 设置ID以便执行更新
                newStep.setPsCode(existing.getPsCode());

                // 执行更新操作
                processStepService.updateByBo(newStep);

                // 从待处理集合移除，剩余的就是需要删除的
                existingSteps.remove(psModel);
            }
            // 4.2 如果步骤不存在，则新增
            else {
                processStepService.insertByBo(newStep);
            }
        }

        // ========== 5. 处理普通流程关系 - 增量更新 ==========

        for (Element flow : flows) {
            // 获取连接线的源和目标元素ID
            String sourceId = flow.attributeValue("sourceRef");
            String targetId = flow.attributeValue("targetRef");

            // 确保源和目标都存在
            if (stepMap.containsKey(sourceId) && stepMap.containsKey(targetId)) {
                // 获取源和目标步骤的业务编码
                String sourceCode = stepMap.get(sourceId).getPsCode();
                String targetCode = stepMap.get(targetId).getPsCode();
                String flowKey = sourceCode + "->" + targetCode;  // 生成关系唯一键

                // 创建流程关系业务对象
                IcesProcessStepPrevBo stepPrevBo = new IcesProcessStepPrevBo();
                stepPrevBo.setProcCode(procCode);
                stepPrevBo.setPsCodePrev(sourceCode);
                stepPrevBo.setPsCodeCur(targetCode);

                // 5.1 如果关系已存在，则更新
                if (existingFlows.containsKey(flowKey)) {
                    IcesProcessStepPrevVo existing = existingFlows.get(flowKey);
                    stepPrevBo.setPspId(existing.getPspId());  // 设置ID以便更新
                    stepPrevService.updateByBo(stepPrevBo);
                    existingFlows.remove(flowKey);  // 从待处理集合移除
                }
                // 5.2 如果关系不存在，则新增
                else {
                    stepPrevService.insertByBo(stepPrevBo);
                }
            }
        }

        // ========== 6. 处理跨轮次流程关系 - 增量更新 ==========

        for (Element task : tasks) {
            // 获取任务的prev属性(JSON格式)
            String prevJson = task.attributeValue("prev");
            if (StringUtils.isNotBlank(prevJson)) {
                // 解析JSON为前驱步骤ID数组
                String[] prevIds = new ObjectMapper().readValue(prevJson, String[].class);
                String currentId = task.attributeValue("id");

                // 确保当前任务存在
                if (stepMap.containsKey(currentId)) {
                    String currentCode = stepMap.get(currentId).getPsCode();

                    // 处理每个前驱步骤
                    for (String prevId : prevIds) {
                        if (stepMap.containsKey(prevId)) {
                            String prevCode = stepMap.get(prevId).getPsCode();
                            String roundFlowKey = prevCode + "->" + currentCode;

                            // 创建跨轮次关系业务对象
                            IcesProcessStepPrevRoundBo stepPrevRoundBo = new IcesProcessStepPrevRoundBo();
                            stepPrevRoundBo.setProcCode(procCode);
                            stepPrevRoundBo.setPsCodePrev(prevCode);
                            stepPrevRoundBo.setPsCodeCur(currentCode);

                            // 6.1 如果关系已存在，则更新
                            if (existingRoundFlows.containsKey(roundFlowKey)) {
                                IcesProcessStepPrevRoundVo existing = existingRoundFlows.get(roundFlowKey);
                                stepPrevRoundBo.setPsprId(existing.getPsprId());  // 设置ID以便更新
                                prevRoundService.updateByBo(stepPrevRoundBo);
                                existingRoundFlows.remove(roundFlowKey);  // 从待处理集合移除
                            }
                            // 6.2 如果关系不存在，则新增
                            else {
                                prevRoundService.insertByBo(stepPrevRoundBo);
                            }
                        }
                    }
                }
            }
        }

        // ========== 7. 处理步骤的原料需求 - 增量更新 ==========

        for (Element task : tasks) {
            // 获取任务的demand属性(JSON格式)
            String demandJson = task.attributeValue("demand");
            if (StringUtils.isNotBlank(demandJson)) {
                // 解析JSON为bo对象
                IcesProcessMaterialBo[] processMaterialBos = new ObjectMapper().readValue(demandJson, IcesProcessMaterialBo[].class);
                String currentId = task.attributeValue("id");

                // 确保当前任务存在
                if (stepMap.containsKey(currentId)) {
                    String currentCode = stepMap.get(currentId).getPsCode();

                    // 处理当前任务的所有原料需求
                    for (IcesProcessMaterialBo processMaterialBo : processMaterialBos) {
                        // 设置原料需求业务对象
                        processMaterialBo.setPsCode(currentCode);
                        processMaterialBo.setProcCode(procCode);

                        // 7.1 如果关系已存在，则更新
                        if (existingProcessMaterials.containsKey(processMaterialBo.getMaCode())) {
                            IcesProcessMaterialVo existing = existingProcessMaterials.get(processMaterialBo.getMaCode());
                            processMaterialBo.setPmId(existing.getPmId());
                            processMaterialService.updateByBo(processMaterialBo);
                            existingProcessMaterials.remove(processMaterialBo.getMaCode());
                        }
                        // 7.2 如果关系不存在，则新增
                        else {
                            processMaterialService.insertByBo(processMaterialBo);
                        }
                    }
                }
            }
        }


        // ========== 8. 清理不再使用的数据 ==========

        // 8.1 删除不再使用的普通流程关系
        if (!existingFlows.isEmpty()) {
            List<Long> flowIdsToDelete = existingFlows.values().stream()
                .map(IcesProcessStepPrevVo::getPspId)
                .collect(Collectors.toList());
            stepPrevService.deleteWithValidByIds(flowIdsToDelete, false);
        }

        // 8.2 删除不再使用的跨轮次流程关系
        if (!existingRoundFlows.isEmpty()) {
            List<Long> roundFlowIdsToDelete = existingRoundFlows.values().stream()
                .map(IcesProcessStepPrevRoundVo::getPsprId)
                .collect(Collectors.toList());
            prevRoundService.deleteWithValidByIds(roundFlowIdsToDelete, false);
        }

        // 8.3 删除不再使用的原料需求
        if (!existingProcessMaterials.isEmpty()) {
            List<Long> pmIdsToDelete = existingProcessMaterials.values().stream()
                .map(IcesProcessMaterialVo::getPmId)
                .collect(Collectors.toList());
            processMaterialService.deleteWithValidByIds(pmIdsToDelete, false);
        }

        // 8.4 删除不再使用的步骤
        if (!existingSteps.isEmpty()) {
            List<Long> stepIdsToDelete = existingSteps.values().stream()
                .map(IcesProcessStepVo::getPsId)
                .collect(Collectors.toList());
            processStepService.deleteWithValidByIds(stepIdsToDelete, false);
        }
    }
}
