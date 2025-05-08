package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.flowable.factory.FlowServiceFactory;
import com.ruoyi.system.domain.bo.IcesExceptionLifecycleVersionBo;
import com.ruoyi.system.domain.vo.IcesExceptionLifecycleVersionVo;
import com.ruoyi.system.service.IIcesCodeService;
import com.ruoyi.system.service.IIcesExceptionLifecycleVersionService;
import lombok.RequiredArgsConstructor;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.flowable.engine.repository.Model;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.IcesExceptionLifecycleBo;
import com.ruoyi.system.domain.vo.IcesExceptionLifecycleVo;
import com.ruoyi.system.domain.IcesExceptionLifecycle;
import com.ruoyi.system.mapper.IcesExceptionLifecycleMapper;
import com.ruoyi.system.service.IIcesExceptionLifecycleService;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 异常生命周期Service业务层处理
 *
 * @author ruoyi
 * @date 2025-04-08
 */
@RequiredArgsConstructor
@Service
public class IcesExceptionLifecycleServiceImpl extends FlowServiceFactory implements IIcesExceptionLifecycleService {

    private final IcesExceptionLifecycleMapper baseMapper;
    private final IIcesCodeService codeService;
    private final IIcesExceptionLifecycleVersionService lifecycleVersionService;

    /**
     * 查询异常生命周期
     */
    @Override
    public IcesExceptionLifecycleVo queryById(Long exlId){
        return baseMapper.selectVoById(exlId);
    }

    /**
     * 查询异常生命周期列表
     */
    @Override
    public TableDataInfo<IcesExceptionLifecycleVo> queryPageList(IcesExceptionLifecycleBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesExceptionLifecycle> lqw = buildQueryWrapper(bo);
        Page<IcesExceptionLifecycleVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询异常生命周期列表
     */
    @Override
    public List<IcesExceptionLifecycleVo> queryList(IcesExceptionLifecycleBo bo) {
        LambdaQueryWrapper<IcesExceptionLifecycle> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesExceptionLifecycle> buildQueryWrapper(IcesExceptionLifecycleBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesExceptionLifecycle> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getExlCode()), IcesExceptionLifecycle::getExlCode, bo.getExlCode());
        lqw.eq(StringUtils.isNotBlank(bo.getExCode()), IcesExceptionLifecycle::getExCode, bo.getExCode());
        lqw.eq(bo.getExlDelete() != null, IcesExceptionLifecycle::getExlDelete, bo.getExlDelete());
        return lqw;
    }

    /**
     * 新增异常生命周期
     */
    @Override
    public Boolean insertByBo(IcesExceptionLifecycleBo bo) {
        bo.setExlCode(codeService.insertByType("ExceptionLifecycle"));
        IcesExceptionLifecycle add = BeanUtil.toBean(bo, IcesExceptionLifecycle.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setExlId(add.getExlId());
        }
        return flag;
    }

    /**
     * 修改异常生命周期
     */
    @Override
    public Boolean updateByBo(IcesExceptionLifecycleBo bo) {
        IcesExceptionLifecycle update = BeanUtil.toBean(bo, IcesExceptionLifecycle.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesExceptionLifecycle entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除异常生命周期
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 保存异常处理流程
     * @param json 前端传来的JSON字符串
     * @return 是否成功
     * @author YangZY
     * @date 20250506
     */
    @Override
    public Boolean saveModel(String json) throws JsonProcessingException, DocumentException {
        // 接收参数
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> data = mapper.readValue(json, Map.class);
        String exlId = data.get("exlId").toString();
        String xml = data.get("xml").toString();

        // 找到生命周期
        IcesExceptionLifecycleVo lifecycleVo = queryById(Long.valueOf(exlId));
        // 找到已有的版本
        IcesExceptionLifecycleVersionBo versionBo = new IcesExceptionLifecycleVersionBo();
        versionBo.setExlCode(lifecycleVo.getExlCode());
        List<IcesExceptionLifecycleVersionVo> versionVos = lifecycleVersionService.queryList(versionBo);

        // 解析XML得到key和name
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
        Element rootElement = document.getRootElement();
        Element process = rootElement.element("process");
        String processId = process.attributeValue("id");
        String processName = process.attributeValue("name");

        int ver = 0;

        if (!versionVos.isEmpty()) {
            // 确定目前最新的版本
            for (IcesExceptionLifecycleVersionVo vo : versionVos) {
                String name = vo.getExlvName().split("v")[1];
                int current = Integer.parseInt(name);
                ver = Math.max(ver, current + 1);
            }
        }

        // 保存模型和XML
        Model model = repositoryService.newModel();
        model.setName(processName);
        model.setKey(processId);
        model.setVersion(ver);
        repositoryService.saveModel(model);
        repositoryService.addModelEditorSource(model.getId(),
            StringUtils.getBytes(xml, StandardCharsets.UTF_8));
        // 更新生命周期版本
        // 生成版本
        IcesExceptionLifecycleVersionBo newVersionBo = new IcesExceptionLifecycleVersionBo();
        newVersionBo.setExlCode(lifecycleVo.getExlCode());
        newVersionBo.setExlvName("v" + ver);
        newVersionBo.setExlvGeId(model.getId());
        lifecycleVersionService.insertByBo(newVersionBo);
        // 更新生命周期
        // 当前版本是最新版本
        IcesExceptionLifecycleBo lifecycleBo = new IcesExceptionLifecycleBo();
        lifecycleBo.setExlId(lifecycleVo.getExlId());
        lifecycleBo.setExlModelId(model.getId());
        lifecycleBo.setExlModelKey(model.getKey());
        updateByBo(lifecycleBo);

        return true;
    }

    /**
     * 获取生命周期最新版本
     * @param exlId 生命周期
     * @return 模型XML字符串
     * @author YangZY
     * @date 20250506
     */
    @Override
    public String getModel(String exlId) {
        // 找到生命周期
        IcesExceptionLifecycleVo lifecycleVo = queryById(Long.valueOf(exlId));
        if (StringUtils.isNotBlank(lifecycleVo.getExlModelId())) {
            byte[] bpmnBytes = repositoryService.getModelEditorSource(lifecycleVo.getExlModelId());
            return StrUtil.utf8Str(bpmnBytes);
        } else {
            return "";
        }
    }

    /**
     * 根据模型ID查模型
     * @param modelId 模型ID
     * @return 模型XML字符串
     * @author YangZY
     * @date 20250506
     */
    @Override
    public String queryBpmnXmlById(String modelId) {
        byte[] bpmnBytes = repositoryService.getModelEditorSource(modelId);
        return StrUtil.utf8Str(bpmnBytes);
    }
}
