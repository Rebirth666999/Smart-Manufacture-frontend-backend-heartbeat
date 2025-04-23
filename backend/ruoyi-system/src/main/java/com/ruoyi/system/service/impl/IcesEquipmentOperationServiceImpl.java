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
import com.ruoyi.system.domain.vo.*;
import com.ruoyi.system.service.*;
import lombok.RequiredArgsConstructor;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.flowable.engine.repository.Model;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.IcesEquipmentOperation;
import com.ruoyi.system.mapper.IcesEquipmentOperationMapper;
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
    private final IIcesCodeService codeService;
    private final IIcesEquipmentOperationStepService operationStepService;
    private final IIcesEquipmentOperationStepParamService stepParamService;
    private final IIcesEquipmentOperationStepPrevService stepPrevService;
    private final IIcesEquipmentService equipmentService;

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
        lqw.eq(StringUtils.isNotBlank(bo.getEoCode()), IcesEquipmentOperation::getEoCode, bo.getEoCode());
        lqw.eq(StringUtils.isNotBlank(bo.getMoCode()), IcesEquipmentOperation::getMoCode, bo.getMoCode());
        lqw.eq(StringUtils.isNotBlank(bo.getEqCode()), IcesEquipmentOperation::getEqCode, bo.getEqCode());
        lqw.eq(bo.getEoDelete() != null, IcesEquipmentOperation::getEoDelete, bo.getEoDelete());
        return lqw;
    }

    /**
     * 新增设备操作
     */
    @Override
    public Boolean insertByBo(IcesEquipmentOperationBo bo) {
        bo.setEoCode(codeService.insertByType("EquipmentOperation"));
        IcesEquipmentOperation add = BeanUtil.toBean(bo, IcesEquipmentOperation.class);
        validEntityBeforeSave(add);
        updateEquipment(bo);
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
        updateEquipment(bo);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 新增/修改设备操作算作对设备的修改
     * 需要更新设备的修改人、修改时间字段
     */
    private void updateEquipment(IcesEquipmentOperationBo bo) {
        // 搜索条件
        String eqCode = bo.getEqCode();
        IcesEquipmentBo equipmentBo = new IcesEquipmentBo();
        equipmentBo.setEqCode(eqCode);
        // 查找列表
        List<IcesEquipmentVo> vos = equipmentService.queryList(equipmentBo);
        assert vos != null && vos.size() == 1;
        // 调用更新（设置字段在目标方法进行）
        equipmentBo.setEqCode(null);
        equipmentBo.setEqId(vos.get(0).getEqId());
        equipmentBo.setEqStat(vos.get(0).getEqStat());
        equipmentService.updateByBo(equipmentBo);
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
        //删除设备操作，或使用级联删除，删除操作步骤等关联表的数据，或置删除字段为1，这里使用后者
        for (Long id : ids) {
            final IcesEquipmentOperation icesEquipmentOperation = baseMapper.selectById(id);
            icesEquipmentOperation.setEoDelete(1L);
            baseMapper.updateById(icesEquipmentOperation);
        }
        System.out.println(ids);
        return true;
//        return baseMapper.deleteBatchIds(ids) > 0;
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
        // 读取基本信息，获取设备操作对象
        String processId = process.attributeValue("id");
        String processName = process.attributeValue("name");
        Long eoId = Long.parseLong(processId.replace("process_", ""));
        IcesEquipmentOperation equipmentOperation = baseMapper.selectById(eoId);
        String eoModel = equipmentOperation.getEoModel();
        String eoCode = equipmentOperation.getEoCode();
        int newModelFlag = 0;  // 是否为新建模型

        // 检查模型是否存在
        Model model = repositoryService.getModel(eoModel);
        if (ObjectUtil.isNull(model)) {
            newModelFlag = 1;
            // 新建模型
            Model newModel = repositoryService.newModel();
            newModel.setName(processName);
            newModel.setKey(processId);
            repositoryService.saveModel(newModel);
            // 保存模型ID
            eoModel = newModel.getId();
            equipmentOperation.setEoModel(eoModel);
            baseMapper.updateById(equipmentOperation);
        }
        // 保存XML
        repositoryService.addModelEditorSource(eoModel ,StringUtils.getBytes(modelXML, StandardCharsets.UTF_8));

        // 不是新模型，先删除原先数据库的所有数据
        if (newModelFlag == 0) {
            // 构造搜索条件
            IcesEquipmentOperationStepBo operationStepBo = new IcesEquipmentOperationStepBo();
            operationStepBo.setEoCode(eoCode);
            // 查找所有符合条件的步骤
            List<IcesEquipmentOperationStepVo> operationStepVos = operationStepService.queryList(operationStepBo);
            // 取出步骤的ID
            List<Long> operationStepIds = new ArrayList<>();
            for (IcesEquipmentOperationStepVo operationStepVo : operationStepVos) {
                operationStepIds.add(operationStepVo.getEosId());
            }

            // 数据库存在步骤需要删除
            if (!operationStepIds.isEmpty()) {
                // 构造搜索条件
                IcesEquipmentOperationStepPrevBo stepPrevBo = new IcesEquipmentOperationStepPrevBo();
                stepPrevBo.setEoCode(eoCode);
                // 查找所有符合条件的前部步骤关系
                List<IcesEquipmentOperationStepPrevVo> stepPrevVos = stepPrevService.queryList(stepPrevBo);
                // 取出关系的ID
                List<Long> stepPrevIds = new ArrayList<>();
                for (IcesEquipmentOperationStepPrevVo stepPrevVo : stepPrevVos) {
                    stepPrevIds.add(stepPrevVo.getEosprId());
                }
                // 删除前序步骤关系
                if (!stepPrevIds.isEmpty()) {
                    stepPrevService.deleteWithValidByIds(stepPrevIds, false);
                }

                // 构造搜索条件
                IcesEquipmentOperationStepParamBo stepParamBo = new IcesEquipmentOperationStepParamBo();
                stepParamBo.setEoCode(eoCode);
                // 查找所有符合条件的步骤参数
                List<IcesEquipmentOperationStepParamVo> stepParamVos = stepParamService.queryList(stepParamBo);
                // 取出参数的ID
                List<Long> stepParamIds = new ArrayList<>();
                for (IcesEquipmentOperationStepParamVo stepParamVo : stepParamVos) {
                    stepParamIds.add(stepParamVo.getEospaId());
                }
                // 删除步骤参数
                if (!stepParamIds.isEmpty()) {
                    stepParamService.deleteWithValidByIds(stepParamIds, false);
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
                step.setEoCode(eoCode);
                steps.add(step);
                // 保存到映射
                stepMap.put(next.attributeValue("id"), step);
            } else if (Objects.equals(next.getName(), "endEvent")) {
                // 结束事件
                IcesEquipmentOperationStepBo step = new IcesEquipmentOperationStepBo();
                step.setEoCode(eoCode);
                steps.add(step);
                // 保存到映射
                stepMap.put(next.attributeValue("id"), step);
            } else if (Objects.equals(next.getName(), "task")) {
                // 操作步骤
                tasks.add(next);
                IcesEquipmentOperationStepBo step = new IcesEquipmentOperationStepBo();
                step.setEoCode(eoCode);
                step.setEaoCode(next.attributeValue("eaoCode"));
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
                    if (StringUtils.isEmpty(paramBo.getEospaCodeParent())) {
                        paramBo.setEospaCodeParent(null);
                    } else {
                        paramBo.setEospaCodeParent(paramBoList.get(Integer.parseInt(paramBo.getEospaCodeParent()) - 1).getEospaCode());
                    }
                    // 设置当前步骤的ID
                    paramBo.setEosCode(taskMap.get(task).getEosCode());
                    paramBo.setEoCode(eoCode);
                    stepParamService.insertByBo(paramBo);
                }
            }
        }
        // 解析flows的各个对象，根据箭头的走向和stepMap，将前序任务关系存进数据库
        for (Element flow: flows) {
            IcesEquipmentOperationStepPrevBo stepPrevBo = new IcesEquipmentOperationStepPrevBo();
            if (stepMap.get(flow.attributeValue("targetRef")) != null)
                stepPrevBo.setEosCodeCur(stepMap.get(flow.attributeValue("targetRef")).getEosCode());
            if (stepMap.get(flow.attributeValue("sourceRef")) != null)
                stepPrevBo.setEosCodePrev(stepMap.get(flow.attributeValue("sourceRef")).getEosCode());
            // 仅在两个ID都存在时插入
            if (StringUtils.isNotBlank(stepPrevBo.getEosCodeCur()) && StringUtils.isNotBlank(stepPrevBo.getEosCodePrev())) {
                stepPrevBo.setEoCode(eoCode);
                stepPrevService.insertByBo(stepPrevBo);
            }
        }

        // 更新设备信息
        IcesEquipmentOperationBo equipmentOperationBo = new IcesEquipmentOperationBo();
        equipmentOperationBo.setEoId(eoId);
        equipmentOperationBo.setEqCode(equipmentOperation.getEqCode());
        updateEquipment(equipmentOperationBo);
    }
}
