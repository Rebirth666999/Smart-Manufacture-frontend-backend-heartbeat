package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.domain.IcesManufacturePlan;
import com.ruoyi.system.domain.bo.*;
import com.ruoyi.system.domain.vo.IcesDeviceTaskVo;
import com.ruoyi.system.domain.vo.IcesManufacturePlanVo;
import com.ruoyi.system.domain.vo.IcesOrderVo;
import com.ruoyi.system.mapper.IcesDeviceTaskMapper;
import com.ruoyi.system.mapper.IcesManufacturePlanMapper;
import com.ruoyi.system.service.IIcesCodeService;
import com.ruoyi.system.service.IIcesManufacturePlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.vo.IcesManufactureTaskVo;
import com.ruoyi.system.domain.IcesManufactureTask;
import com.ruoyi.system.mapper.IcesManufactureTaskMapper;
import com.ruoyi.system.service.IIcesManufactureTaskService;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 生产任务Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-17
 */
@RequiredArgsConstructor
@Service
public class IcesManufactureTaskServiceImpl implements IIcesManufactureTaskService {

    private final IcesManufactureTaskMapper baseMapper;
    private final IIcesCodeService codeService;
    private final IIcesManufacturePlanService icesManufacturePlanService;

    /**
     * 查询生产任务
     */
    @Override
    public IcesManufactureTaskVo queryById(Long mtId){
        return baseMapper.selectVoById(mtId);
    }

    /**
     * 查询生产任务列表
     */
    @Override
    public TableDataInfo<IcesManufactureTaskVo> queryPageList(IcesManufactureTaskBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesManufactureTask> lqw = buildQueryWrapper(bo);
        Page<IcesManufactureTaskVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    @Override
    public TableDataInfo<IcesManufactureTaskVo> queryReviewList(IcesManufactureTaskBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesManufactureTask> lqw = buildQueryWrapper(bo);
        List<String> stats = new ArrayList<>();
        stats.add("2");  // 待审核
        stats.add("3");  // 审核中
        stats.add("a");  // 待审核（弃用）
        stats.add("b");  // 审核中（弃用）
        stats.add("7");  // 待审核（修改）
        stats.add("8");  // 审核中(弃用）
        lqw.in(IcesManufactureTask::getMtStat, stats);
        Page<IcesManufactureTaskVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }


    /**
     * 查询生产任务列表
     */
    @Override
    public List<IcesManufactureTaskVo> queryList(IcesManufactureTaskBo bo) {
        LambdaQueryWrapper<IcesManufactureTask> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesManufactureTask> buildQueryWrapper(IcesManufactureTaskBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesManufactureTask> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getMtCode()), IcesManufactureTask::getMtCode, bo.getMtCode());
        lqw.eq(StringUtils.isNotBlank(bo.getMpCode()), IcesManufactureTask::getMpCode, bo.getMpCode());
        lqw.eq(StringUtils.isNotBlank(bo.getArCode()), IcesManufactureTask::getArCode, bo.getArCode());
        lqw.eq(StringUtils.isNotBlank(bo.getProcCode()), IcesManufactureTask::getProcCode, bo.getProcCode());
        lqw.eq(StringUtils.isNotBlank(bo.getMtStat()), IcesManufactureTask::getMtStat, bo.getMtStat());
        lqw.eq(bo.getMtPriority() != null, IcesManufactureTask::getMtPriority, bo.getMtPriority());
        lqw.eq(bo.getMtDelete() != null, IcesManufactureTask::getMtDelete, bo.getMtDelete());
        return lqw;
    }

    /**
     * 新增生产任务
     */
    @Override
    public Boolean insertByBo(IcesManufactureTaskBo bo) {
        bo.setMtCode(codeService.insertByType("ManufactureTask"));
        updateManufacturePlan(bo);
        IcesManufactureTask add = BeanUtil.toBean(bo, IcesManufactureTask.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setMtCode(add.getMtCode());
        }
        return flag;
    }

    /**
     * 修改生产任务
     */
    @Override
    public Boolean updateByBo(IcesManufactureTaskBo bo) {
        IcesManufactureTask update = BeanUtil.toBean(bo, IcesManufactureTask.class);
        validEntityBeforeSave(update);
        // bo是否已完成
        if(Objects.equals(bo.getMtCode(),"6")){
            IcesManufactureTaskBo icesManufactureTaskBo = new IcesManufactureTaskBo();
            icesManufactureTaskBo.setMtCode(bo.getMtCode());
            List<IcesManufactureTaskVo> otherVo=queryList(bo);
            int done=1;
            for(IcesManufactureTaskVo vo:otherVo){
                if(!vo.getMtStat().equals(bo.getMtStat())){
                    done=0;
                    break;
                }
            }
            if(done==1){//任务已经全部完成
                icesManufacturePlanService.updateStatus(bo);
            }
        }
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 新增生产任务算作下发生产计划
     */
    private void updateManufacturePlan(IcesManufactureTaskBo bo) {
        // 搜索条件
        String mpCode = bo.getMpCode();
        IcesManufacturePlanBo manufacturePlanBo = new IcesManufacturePlanBo();
        manufacturePlanBo.setMpCode(mpCode);
        // 查找列表
        List<IcesManufacturePlanVo> vos = icesManufacturePlanService.queryList(manufacturePlanBo);
        assert vos != null && vos.size() == 1;
        // 调用更新
        String rMan = getLoginUsername();
        String rDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        manufacturePlanBo.setMpCode(null);
        manufacturePlanBo.setMpId(vos.get(0).getMpId());
        manufacturePlanBo.setMpRman(rMan);
        manufacturePlanBo.setMpRdate(rDate);
        icesManufacturePlanService.updateByBo(manufacturePlanBo);
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
    private void validEntityBeforeSave(IcesManufactureTask entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除生产任务
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public void updateStatus(IcesDeviceTaskBo deviceTaskBo) {
        IcesManufactureTaskBo bo = new IcesManufactureTaskBo();
        bo.setMtCode(deviceTaskBo.getMtCode());
        List<IcesManufactureTaskVo> icesManufactureTaskVos = queryList(bo);
        bo.setMtId(icesManufactureTaskVos.get(0).getMtId());
        updateByBo(bo);
    }
}
