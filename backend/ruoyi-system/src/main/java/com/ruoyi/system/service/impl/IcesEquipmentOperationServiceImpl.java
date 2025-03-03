package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.flowable.factory.FlowServiceFactory;
import com.ruoyi.system.domain.IcesEquipmentOperationStep;
import lombok.RequiredArgsConstructor;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.flowable.engine.repository.Model;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.IcesEquipmentOperationBo;
import com.ruoyi.system.domain.vo.IcesEquipmentOperationVo;
import com.ruoyi.system.domain.IcesEquipmentOperation;
import com.ruoyi.system.mapper.IcesEquipmentOperationMapper;
import com.ruoyi.system.service.IIcesEquipmentOperationService;

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
    public void saveModel(String modelXML) throws DocumentException {
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
            queryById(eoId).setEoModel(modelId);
        }
        // 保存XML
        repositoryService.addModelEditorSource(modelId ,StringUtils.getBytes(modelXML, StandardCharsets.UTF_8));

        // 读取XML中的模型
        Iterator<Element> iterator = process.elementIterator();
        List<Element> tasks = new ArrayList<>();  // 所有任务节点
        List<Element> flows = new ArrayList<>();  // 所有连接线
        List<IcesEquipmentOperationStep> steps = new ArrayList<>();
        Map<String, IcesEquipmentOperationStep> stepMap = new HashMap<>();
        while (iterator.hasNext()) {
            Element next = iterator.next();
            // 解析当前节点
            if (Objects.equals(next.getName(), "startEvent")) {
                // 开始事件
                IcesEquipmentOperationStep step = new IcesEquipmentOperationStep();
                step.setEoId(eoId);
                steps.add(step);
                stepMap.put(next.attributeValue("id"), step);
            } else if (Objects.equals(next.getName(), "endEvent")) {
                // 结束事件
                IcesEquipmentOperationStep step = new IcesEquipmentOperationStep();
                step.setEoId(eoId);
                steps.add(step);
                stepMap.put(next.attributeValue("id"), step);
            } else if (Objects.equals(next.getName(), "Task")) {
                // 操作步骤
                tasks.add(next);
                IcesEquipmentOperationStep step = new IcesEquipmentOperationStep();
                step.setEoId(eoId);
                step.setEaoId(Long.parseLong(next.attributeValue("eaoId")));
                step.setEosDesc(next.attributeValue("eosDesc"));
                steps.add(step);
                stepMap.put(next.attributeValue("id"), step);
            } else if (Objects.equals(next.getName(), "sequenceFlow")) {
                // 连接线
                flows.add(next);
            }
        }
        // TODO 向数据库插入steps中所有对象，获取各个对象的ID
        // TODO 解析tasks数组的各个对象，获取各任务的参数params，将步骤参数存进数据库
        // TODO 解析flows的各个对象，根据箭头的走向和stepMap，将前序任务关系存进数据库
    }
}
