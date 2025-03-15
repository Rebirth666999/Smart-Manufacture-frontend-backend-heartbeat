package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.flowable.factory.FlowServiceFactory;
import com.ruoyi.system.domain.IcesEquipmentOperationStep;
import com.ruoyi.system.domain.IcesEquipmentOperationStepPrev;
import com.ruoyi.system.domain.bo.*;
import com.ruoyi.system.domain.vo.IcesEquipmentOperationStepParamVo;
import com.ruoyi.system.domain.vo.IcesEquipmentOperationStepPrevVo;
import com.ruoyi.system.domain.vo.IcesEquipmentOperationStepVo;
import com.ruoyi.system.service.IIcesEquipmentOperationStepParamService;
import com.ruoyi.system.service.IIcesEquipmentOperationStepPrevService;
import com.ruoyi.system.service.IIcesEquipmentOperationStepService;
import lombok.RequiredArgsConstructor;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.flowable.engine.repository.Model;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.vo.IcesEquipmentOperationVo;
import com.ruoyi.system.domain.IcesEquipmentOperation;
import com.ruoyi.system.mapper.IcesEquipmentOperationMapper;
import com.ruoyi.system.service.IIcesEquipmentOperationService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 设备操作Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-02
 */
@RequiredArgsConstructor
@Service
public class IcesEquipmentOperationServiceImpl extends FlowServiceFactory implements IIcesEquipmentOperationService {

    private final IcesEquipmentOperationMapper baseMapper;
    private final IIcesEquipmentOperationStepService operationStepService;
    private final IIcesEquipmentOperationStepParamService stepParamService;
    private final IIcesEquipmentOperationStepPrevService stepPrevService;

    /**
     * 查询设备操作
     */
    @Override
    public IcesEquipmentOperationVo queryById(Long eoId){
        return baseMapper.selectVoById(eoId);
    }

    /**
     * 查询设备操作列表
     */
    @Override
    public TableDataInfo<IcesEquipmentOperationVo> queryPageList(IcesEquipmentOperationBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesEquipmentOperation> lqw = buildQueryWrapper(bo);
        Page<IcesEquipmentOperationVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询设备操作列表
     */
    @Override
    public List<IcesEquipmentOperationVo> queryList(IcesEquipmentOperationBo bo) {
        LambdaQueryWrapper<IcesEquipmentOperation> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesEquipmentOperation> buildQueryWrapper(IcesEquipmentOperationBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesEquipmentOperation> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getMoId() != null, IcesEquipmentOperation::getMoId, bo.getMoId());
        lqw.eq(bo.getEqId() != null, IcesEquipmentOperation::getEqId, bo.getEqId());
        lqw.eq(bo.getEoDelete() != null, IcesEquipmentOperation::getEoDelete, bo.getEoDelete());
        return lqw;
    }

    /**
     * 新增设备操作
     */
    @Override
    public Boolean insertByBo(IcesEquipmentOperationBo bo) {
        IcesEquipmentOperation add = BeanUtil.toBean(bo, IcesEquipmentOperation.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setEoId(add.getEoId());
        }
        return flag;
    }

    /**
     * 修改设备操作
     */
    @Override
    public Boolean updateByBo(IcesEquipmentOperationBo bo) {
        IcesEquipmentOperation update = BeanUtil.toBean(bo, IcesEquipmentOperation.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesEquipmentOperation entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除设备操作
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
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
    public void saveModel(String modelXML) throws DocumentException, JsonProcessingException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new ByteArrayInputStream(modelXML.getBytes(StandardCharsets.UTF_8)));
        Element rootElement = document.getRootElement();
        Element process = rootElement.element("process");  // 定位到process
        // 读取基本信息，获取设备操作ID
        String processId = process.attributeValue("id");
        String processName = process.attributeValue("name");
        Long eoId = Long.parseLong(processId.replace("process_", ""));
        String modelId = queryById(eoId).getEoModel();
        int newModelFlag = 0;  // 是否为新建模型
        // TODO 如果不是新建模型，则应当在插入新内容之前，先把数据库中旧的实体全部删掉

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
            IcesEquipmentOperation equipmentOperation = baseMapper.selectById(eoId);
            equipmentOperation.setEoModel(modelId);
            baseMapper.updateById(equipmentOperation);
        }
        // 保存XML
        repositoryService.addModelEditorSource(modelId ,StringUtils.getBytes(modelXML, StandardCharsets.UTF_8));

        // 不是新模型，先删除原先数据库的所有数据
        if (newModelFlag == 0) {
            // 构造搜索条件
            IcesEquipmentOperationStepBo bo = new IcesEquipmentOperationStepBo();
            bo.setEoId(eoId);
            // 查找所有符合条件的步骤
            List<IcesEquipmentOperationStepVo> operationStepVos = operationStepService.queryList(bo);
            // 取出步骤的ID
            List<Long> operationStepIds = new ArrayList<>();
            for (IcesEquipmentOperationStepVo operationStepVo : operationStepVos) {
                operationStepIds.add(operationStepVo.getEosId());
            }
            // 数据库存在步骤需要删除
            if (!operationStepIds.isEmpty()) {
                // 用于删除的集合
                List<Long> deleteIds = new ArrayList<>();

                // 找到所有前序步骤关系
                List<IcesEquipmentOperationStepPrevVo> operationStepPrevVos = stepPrevService.queryList(new IcesEquipmentOperationStepPrevBo());
                // 找到所有与步骤有关的前序关系
                for (IcesEquipmentOperationStepPrevVo operationStepPrevVo : operationStepPrevVos) {
                    if (operationStepIds.contains(operationStepPrevVo.getEosIdCur()) || operationStepIds.contains(operationStepPrevVo.getEosIdPrev())) {
                        deleteIds.add(operationStepPrevVo.getEosprId());
                    }
                }
                // 删除前序关系
                if (!deleteIds.isEmpty()) {
                    stepPrevService.deleteWithValidByIds(deleteIds, false);
                    deleteIds.clear();
                }


                IcesEquipmentOperationStepParamBo paramBo = new IcesEquipmentOperationStepParamBo();
                for (Long id: operationStepIds) {
                    // 构造搜索条件
                    paramBo.setEosId(id);
                    // 找到步骤参数
                    List<IcesEquipmentOperationStepParamVo> paramVos = stepParamService.queryList(paramBo);
                    // id全部放进集合
                    for (IcesEquipmentOperationStepParamVo paramVo : paramVos) {
                        deleteIds.add(paramVo.getEospaId());
                    }
                }
                // 删除步骤参数
                if (!deleteIds.isEmpty()) {
                    stepParamService.deleteWithValidByIds(deleteIds, false);
                }

                // 最后删除步骤
                operationStepService.deleteWithValidByIds(operationStepIds, false);
            }
        }

        // 读取XML中的模型
        Iterator<Element> iterator = process.elementIterator();
        List<Element> tasks = new ArrayList<>();  // 所有任务节点
        List<Element> flows = new ArrayList<>();  // 所有连接线
        List<IcesEquipmentOperationStepBo> steps = new ArrayList<>();  // 步骤实体组成的数组
        Map<String, IcesEquipmentOperationStepBo> stepMap = new HashMap<>();  // XML中对象id和步骤实体的映射
        Map<Element, IcesEquipmentOperationStepBo> taskMap = new HashMap<>();  // XML对象和步骤实体的映射
        while (iterator.hasNext()) {
            Element next = iterator.next();
            // 解析当前节点
            if (Objects.equals(next.getName(), "startEvent")) {
                // 开始事件
                IcesEquipmentOperationStepBo step = new IcesEquipmentOperationStepBo();
                step.setEoId(eoId);
                steps.add(step);
                // 保存到映射
                stepMap.put(next.attributeValue("id"), step);
            } else if (Objects.equals(next.getName(), "endEvent")) {
                // 结束事件
                IcesEquipmentOperationStepBo step = new IcesEquipmentOperationStepBo();
                step.setEoId(eoId);
                steps.add(step);
                // 保存到映射
                stepMap.put(next.attributeValue("id"), step);
            } else if (Objects.equals(next.getName(), "task")) {
                // 操作步骤
                tasks.add(next);
                IcesEquipmentOperationStepBo step = new IcesEquipmentOperationStepBo();
                step.setEoId(eoId);
                step.setEaoId(Long.parseLong(next.attributeValue("eaoId")));
                step.setEosDesc(next.attributeValue("eosDesc"));
                steps.add(step);
                // 保存到映射
                stepMap.put(next.attributeValue("id"), step);
                taskMap.put(next, step);
            } else if (Objects.equals(next.getName(), "sequenceFlow")) {
                // 连接线
                flows.add(next);
            }
        }
        // 向数据库插入steps中所有对象，获取各个对象的ID
        for (IcesEquipmentOperationStepBo step: steps) {
            operationStepService.insertByBo(step);
        }
        // 解析tasks数组的各个对象，获取各任务的参数params，将步骤参数存进数据库
        for (Element task: tasks) {
            if (ObjectUtil.isNotNull(task.attributeValue("params"))) {
                // 解析JSON格式的参数列表
                ObjectMapper objectMapper = new ObjectMapper();
                List<IcesEquipmentOperationStepParamBo> paramBoList = objectMapper.readValue(task.attributeValue("params"), objectMapper.getTypeFactory().constructCollectionType(List.class, IcesEquipmentOperationStepParamBo.class));
                // 对于每个参数
                for (IcesEquipmentOperationStepParamBo paramBo: paramBoList) {
                    // 把数组下标解析为实际的参数ID
                    if (paramBo.getEospaIdParent() == 0) {
                        paramBo.setEospaIdParent(null);
                    } else {
                        paramBo.setEospaIdParent(paramBoList.get((int) (paramBo.getEospaIdParent() - 1)).getEospaId());
                    }
                    // 设置当前步骤的ID
                    paramBo.setEosId(taskMap.get(task).getEosId());
                    stepParamService.insertByBo(paramBo);
                }
            }
        }
        // 解析flows的各个对象，根据箭头的走向和stepMap，将前序任务关系存进数据库
        for (Element flow: flows) {
            IcesEquipmentOperationStepPrevBo stepPrevBo = new IcesEquipmentOperationStepPrevBo();
            if (stepMap.get(flow.attributeValue("targetRef")) != null)
                stepPrevBo.setEosIdCur(stepMap.get(flow.attributeValue("targetRef")).getEosId());
            if (stepMap.get(flow.attributeValue("sourceRef")) != null)
                stepPrevBo.setEosIdPrev(stepMap.get(flow.attributeValue("sourceRef")).getEosId());
            // 仅在两个ID都存在时插入
            if (stepPrevBo.getEosIdCur() != null && stepPrevBo.getEosIdPrev() != null) {
                stepPrevService.insertByBo(stepPrevBo);
            }
        }
    }
}
