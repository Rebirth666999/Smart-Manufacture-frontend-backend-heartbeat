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
import com.esotericsoftware.minlog.Log;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.flowable.common.constant.ProcessConstants;
import com.ruoyi.flowable.common.enums.ProcessStatus;
import com.ruoyi.flowable.factory.FlowServiceFactory;
import com.ruoyi.flowable.utils.TaskUtils;
import com.ruoyi.system.domain.IcesExceptionRecord;
import com.ruoyi.system.domain.bo.*;
import com.ruoyi.system.domain.vo.IcesExceptionLifecycleVersionVo;
import com.ruoyi.system.domain.vo.IcesExceptionLifecycleVo;
import com.ruoyi.system.domain.vo.IcesExceptionRecordVo;
import com.ruoyi.system.mapper.IcesExceptionRecordMapper;
import com.ruoyi.system.service.IIcesCodeService;
import com.ruoyi.system.service.IIcesExceptionLifecycleService;
import com.ruoyi.system.service.IIcesExceptionLifecycleVersionService;
import lombok.RequiredArgsConstructor;
import org.flowable.bpmn.constants.BpmnXMLConstants;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.vo.IcesExceptionRecordNewVo;
import com.ruoyi.system.domain.IcesExceptionRecordNew;
import com.ruoyi.system.mapper.IcesExceptionRecordNewMapper;
import com.ruoyi.system.service.IIcesExceptionRecordNewService;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.baomidou.mybatisplus.extension.toolkit.Db.getById;
import static com.baomidou.mybatisplus.extension.toolkit.Db.updateById;

/**
 * 异常记录（新）Service业务层处理
 *
 * @author ruoyi
 * @date 2025-07-30
 */
@RequiredArgsConstructor
@Service
public class IcesExceptionRecordNewServiceImpl extends FlowServiceFactory implements IIcesExceptionRecordNewService {

    private final IcesExceptionRecordNewMapper baseMapper;
    private final IIcesCodeService codeService;
    private final IIcesExceptionLifecycleService lifecycleService;
    private final IIcesExceptionLifecycleVersionService lifecycleVersionService;

    /**
     * 查询异常记录（新）
     */
    @Override
    public IcesExceptionRecordNewVo queryById(Long exrId){
        return baseMapper.selectVoById(exrId);
    }

    /**
     * 查询异常记录（新）列表
     */
    @Override
    public TableDataInfo<IcesExceptionRecordNewVo> queryPageList(IcesExceptionRecordNewBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesExceptionRecordNew> lqw = buildQueryWrapper(bo);
        Page<IcesExceptionRecordNewVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询异常记录（新）列表
     */
    @Override
    public List<IcesExceptionRecordNewVo> queryList(IcesExceptionRecordNewBo bo) {
        LambdaQueryWrapper<IcesExceptionRecordNew> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesExceptionRecordNew> buildQueryWrapper(IcesExceptionRecordNewBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesExceptionRecordNew> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getExrCode()), IcesExceptionRecordNew::getExrCode, bo.getExrCode());
        lqw.eq(StringUtils.isNotBlank(bo.getExsCode()), IcesExceptionRecordNew::getExsCode, bo.getExsCode());
        lqw.eq(StringUtils.isNotBlank(bo.getExCode()), IcesExceptionRecordNew::getExCode, bo.getExCode());
        lqw.eq(StringUtils.isNotBlank(bo.getExrStat()), IcesExceptionRecordNew::getExrStat, bo.getExrStat());
        lqw.eq(StringUtils.isNotBlank(bo.getExrLevel()), IcesExceptionRecordNew::getExrLevel, bo.getExrLevel());
        lqw.between(params.get("beginExrCdate") != null && params.get("endExrCdate") != null,
            IcesExceptionRecordNew::getExrCdate ,params.get("beginExrCdate"), params.get("endExrCdate"));
        lqw.eq(StringUtils.isNotBlank(bo.getExrHumCls()), IcesExceptionRecordNew::getExrHumCls, bo.getExrHumCls());
        lqw.eq(bo.getExrDelete() != null, IcesExceptionRecordNew::getExrDelete, bo.getExrDelete());
        return lqw;
    }

    /**
     * 新增异常记录（新）
     */
    @Override
    public IcesExceptionRecordNewVo insertByBo(IcesExceptionRecordNewBo bo) {
        bo.setExrCode(codeService.insertByType("ExceptionRecord"));
        // 设置上报时间
        String cDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        bo.setExrCdate(cDate);
        // 如果未填写上报人，则使用当前用户
        if (StringUtils.isBlank(bo.getExrUserReport())) {
            bo.setExrUserReport(getLoginUsername());
        }
        if (bo.getExrStat().equals("4")) {//如果状态为异常，则启动生命周期
            bo.setExrProcess(startLifecycle(bo));
        }
        IcesExceptionRecordNew add = BeanUtil.toBean(bo, IcesExceptionRecordNew.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setExrId(add.getExrId());
        }
        return queryById(add.getExrId());
    }
    /**
     * 新增异常记录ai!!!!!!!!!!!!!
     */
    @Override
    public IcesExceptionRecordNewVo insertByBo(IcesExceptionRecordAiBo bo) {
        bo.setExrCode(codeService.insertByType("ExceptionRecord"));
        // 设置上报时间
        String cDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        bo.setExrCdate(cDate);
        // 如果未填写上报人，则使用当前用户
        if (StringUtils.isBlank(bo.getExrUserReport())) {
            bo.setExrUserReport(getLoginUsername());
        }
        if (bo.getExrStat().equals("4")) {
            bo.setExrProcess(startLifecycle(bo));
        }
        IcesExceptionRecordNew add = BeanUtil.toBean(bo, IcesExceptionRecordNew.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setExrId(add.getExrId());
        }
        return queryById(add.getExrId());
    }

    /**
     * 修改异常记录（新）
     */
    @Override
    public Boolean updateByBo(IcesExceptionRecordNewBo bo) {
        // 先找到原先的异常记录
        IcesExceptionRecordNewVo orgn = queryById(bo.getExrId());
        // if (orgn.getExrStat().equals("2") && bo.getExrStat().equals("4")) {
        //     // 原先确认中，现在确认为异常
        //     // 自动启动对应异常的生命周期
        //     bo.setExrProcess(startLifecycle(bo));
        // }
        IcesExceptionRecordNew update = BeanUtil.toBean(bo, IcesExceptionRecordNew.class);
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
     * 启动异常的生命周期（支持 IcesExceptionRecordBo）
     */
    @Override
    public String startLifecycle(IcesExceptionRecordNewBo bo) {
        return doStartLifecycle(bo.getExrCode());
    }

    /**
     * 启动异常的生命周期（支持 IcesExceptionRecordAiBo）
     */
    public String startLifecycle(IcesExceptionRecordAiBo bo) {
        return doStartLifecycle(bo.getExrCode());
    }

    // 启动生命周期后，更新异常记录的流程实例ID
    public String startLifecycleNew(IcesExceptionRecordNewBo bo) {
    try {
        // ✅ 添加参数验证
        if (bo == null) {
            throw new IllegalArgumentException("异常记录对象不能为空");
        }
        if (bo.getExrId() == null) {
            throw new IllegalArgumentException("异常记录ID不能为空");
        }

        // ✅ 验证记录是否存在
        IcesExceptionRecordNewVo existing = baseMapper.selectVoById(bo.getExrId());
        if (existing == null) {
            throw new RuntimeException("未找到ID为 " + bo.getExrId() + " 的异常记录");
        }

        // ✅ 检查是否已经启动过
        if (StringUtils.isNotBlank(existing.getExrProcess())) {
            throw new RuntimeException("该异常记录已启动生命周期，流程实例ID：" + existing.getExrProcess());
        }

        // 1. 启动生命周期，获取流程实例ID
        String processId = doStartLifecycle(bo.getExrCode()); 
        
        // 2. ✅ 更新数据库记录
        IcesExceptionRecordNew update = new IcesExceptionRecordNew();
        update.setExrId(bo.getExrId());
        update.setExrProcess(processId);
        update.setUpdateTime(new Date());
        
        int updateResult = baseMapper.updateById(update);
        if (updateResult <= 0) {
            throw new ServiceException("更新异常记录流程信息失败，可能记录不存在");
        }
        return processId;

    } catch (Exception e) {

        throw new ServiceException("生命周期启动失败: " + e.getMessage());
    }
    }

    /**
     * 启动异常生命周期的核心逻辑
     */
    private String doStartLifecycle(String exrCode) {
        // 找到生命周期
        IcesExceptionLifecycleBo lifecycleBo = new IcesExceptionLifecycleBo();
        lifecycleBo.setExrCode(exrCode);
        Log.info(lifecycleBo.toString());
        List<IcesExceptionLifecycleVo> lifecycleVos = lifecycleService.queryList(lifecycleBo);
        Log.info(lifecycleVos.toString());
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
                Log.info(lifecycleVersionVo.toString());
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
            ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), variables);
            return processInstance.getId();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("异常生命周期启动错误");
        }
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesExceptionRecordNew entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除异常记录（新）
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 保存分析内容到异常记录
     * 将提取的内容保存到exrPro字段（处理流程）
     */
    @Override
    public boolean saveAnalysisContent(Long exrId, String analysisContent,long exrStat) {
        // 查询是否存在该异常记录
        IcesExceptionRecordNew record = getById(exrId,IcesExceptionRecordNew.class);

        if (record == null) {
            System.out.println("service查询后说异常记录不存在");
            return false;
        }

        // 设置处理流程内容（使用exrPro字段）
        record.setExrPro(analysisContent);
        // 更新时间
        record.setUpdateTime(new Date());
        record.setExrStat(String.valueOf(exrStat));

        // 保存到数据库
        return updateById(record);
    }
}
