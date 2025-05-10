package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.flowable.common.constant.ProcessConstants;
import com.ruoyi.flowable.common.enums.ProcessStatus;
import com.ruoyi.flowable.factory.FlowServiceFactory;
import com.ruoyi.flowable.utils.TaskUtils;
import com.ruoyi.system.domain.bo.IcesExceptionLifecycleBo;
import com.ruoyi.system.domain.bo.IcesExceptionLifecycleVersionBo;
import com.ruoyi.system.domain.vo.IcesExceptionLifecycleVersionVo;
import com.ruoyi.system.domain.vo.IcesExceptionLifecycleVo;
import com.ruoyi.system.service.IIcesCodeService;
import com.ruoyi.system.service.IIcesExceptionLifecycleService;
import com.ruoyi.system.service.IIcesExceptionLifecycleVersionService;
import lombok.RequiredArgsConstructor;
import org.flowable.bpmn.constants.BpmnXMLConstants;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.IcesExceptionRecordBo;
import com.ruoyi.system.domain.vo.IcesExceptionRecordVo;
import com.ruoyi.system.domain.IcesExceptionRecord;
import com.ruoyi.system.mapper.IcesExceptionRecordMapper;
import com.ruoyi.system.service.IIcesExceptionRecordService;

import java.util.*;

/**
 * 异常记录Service业务层处理
 *
 * @author ruoyi
 * @date 2025-04-09
 */
@RequiredArgsConstructor
@Service
public class IcesExceptionRecordServiceImpl extends FlowServiceFactory implements IIcesExceptionRecordService {

    private final IcesExceptionRecordMapper baseMapper;
    private final IIcesCodeService codeService;
    private final IIcesExceptionLifecycleService lifecycleService;
    private final IIcesExceptionLifecycleVersionService lifecycleVersionService;

    /**
     * 查询异常记录
     */
    @Override
    public IcesExceptionRecordVo queryById(Long exrId){
        return baseMapper.selectVoById(exrId);
    }

    /**
     * 查询异常记录列表
     */
    @Override
    public TableDataInfo<IcesExceptionRecordVo> queryPageList(IcesExceptionRecordBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesExceptionRecord> lqw = buildQueryWrapper(bo);
        Page<IcesExceptionRecordVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询异常记录列表
     */
    @Override
    public List<IcesExceptionRecordVo> queryList(IcesExceptionRecordBo bo) {
        LambdaQueryWrapper<IcesExceptionRecord> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesExceptionRecord> buildQueryWrapper(IcesExceptionRecordBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesExceptionRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getExrCode()), IcesExceptionRecord::getExrCode, bo.getExrCode());
        lqw.eq(StringUtils.isNotBlank(bo.getExsCode()), IcesExceptionRecord::getExsCode, bo.getExsCode());
        lqw.eq(StringUtils.isNotBlank(bo.getExCode()), IcesExceptionRecord::getExCode, bo.getExCode());
        lqw.eq(StringUtils.isNotBlank(bo.getExrStat()), IcesExceptionRecord::getExrStat, bo.getExrStat());
        lqw.eq(StringUtils.isNotBlank(bo.getExrLevel()), IcesExceptionRecord::getExrLevel, bo.getExrLevel());
        lqw.eq(bo.getExrDelete() != null, IcesExceptionRecord::getExrDelete, bo.getExrDelete());
        return lqw;
    }

    /**
     * 新增异常记录
     */
    @Override
    public IcesExceptionRecordVo insertByBo(IcesExceptionRecordBo bo) {
        bo.setExrCode(codeService.insertByType("ExceptionRecord"));
        // 如果未填写上报人，则使用当前用户
        if (StringUtils.isBlank(bo.getExrUserReport())) {
            bo.setExrUserReport(getLoginUsername());
        }
        IcesExceptionRecord add = BeanUtil.toBean(bo, IcesExceptionRecord.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setExrId(add.getExrId());
        }
        return queryById(add.getExrId());
    }

    /**
     * 修改异常记录
     */
    @Override
    public Boolean updateByBo(IcesExceptionRecordBo bo) {
        // 先找到原先的异常记录
        IcesExceptionRecordVo orgn = queryById(bo.getExrId());
        if (orgn.getExrStat().equals("2") && bo.getExrStat().equals("4")) {
            // 原先确认中，现在确认为异常
            // 自动启动对应异常的生命周期
            startLifecycle(bo);
        }
        IcesExceptionRecord update = BeanUtil.toBean(bo, IcesExceptionRecord.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 获取当前用户名
     * @return 用户名
     */
    private String getLoginUsername() {
        LoginUser loginUser;
        try {
            loginUser = LoginHelper.getLoginUser();
        } catch (Exception e) {
            return null;
        }
        if (ObjectUtil.isNotNull(loginUser)) {
            return loginUser.getUsername();
        } else {
            return null;
        }
    }

    /**
     * 启动异常的生命周期
     * @param bo 异常上报记录
     */
    @Override
    public void startLifecycle(IcesExceptionRecordBo bo) {
        // 找到生命周期
        IcesExceptionLifecycleBo lifecycleBo = new IcesExceptionLifecycleBo();
        lifecycleBo.setExCode(bo.getExCode());
        List<IcesExceptionLifecycleVo> lifecycleVos = lifecycleService.queryList(lifecycleBo);
        if (lifecycleVos.isEmpty()) {
            throw new RuntimeException("对应异常未定义生命周期，无法启动处理");
        }
        // 找到生命周期版本
        IcesExceptionLifecycleVersionBo lifecycleVersionBo = new IcesExceptionLifecycleVersionBo();
        lifecycleVersionBo.setExlCode(lifecycleVos.get(0).getExlCode());
        List<IcesExceptionLifecycleVersionVo> lifecycleVersionVos = lifecycleVersionService.queryList(lifecycleVersionBo);
        if (lifecycleVersionVos.isEmpty()) {
            throw new RuntimeException("对应异常生命周期未设计，无法启动处理");
        }
        // 最后创建的永远是最新版本，因此先逆序
        Collections.reverse(lifecycleVersionVos);
        // 找到最新的已部署版本的流程定义
        String defId = null;
        for (IcesExceptionLifecycleVersionVo lifecycleVersionVo : lifecycleVersionVos) {
            if (lifecycleVersionVo.getExlvDefId() != null) {
                defId = lifecycleVersionVo.getExlvDefId();
                break;
            }
        }
        if (defId == null) {
            throw new RuntimeException("对应异常生命周期未部署，无法启动处理");
        }
        try {
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(defId).singleResult();
            Map<String, Object> variables = new HashMap<>();
            // 设置流程发起人Id到流程中
            String userIdStr = TaskUtils.getUserId();
            identityService.setAuthenticatedUserId(userIdStr);
            variables.put(BpmnXMLConstants.ATTRIBUTE_EVENT_START_INITIATOR, userIdStr);
            // 设置流程状态为进行中
            variables.put(ProcessConstants.PROCESS_STATUS_KEY, ProcessStatus.RUNNING.getStatus());
            // 启动流程实例
            runtimeService.startProcessInstanceById(processDefinition.getId(), variables);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("异常生命周期启动错误");
        }
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesExceptionRecord entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除异常记录
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
