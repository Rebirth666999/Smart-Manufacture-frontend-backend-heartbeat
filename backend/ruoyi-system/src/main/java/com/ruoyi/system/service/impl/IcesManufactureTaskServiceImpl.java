package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.domain.IcesManufacturePlan;
import com.ruoyi.system.domain.bo.IcesManufacturePlanBo;
import com.ruoyi.system.domain.vo.IcesManufacturePlanVo;
import com.ruoyi.system.service.IIcesCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.IcesManufactureTaskBo;
import com.ruoyi.system.domain.vo.IcesManufactureTaskVo;
import com.ruoyi.system.domain.IcesManufactureTask;
import com.ruoyi.system.mapper.IcesManufactureTaskMapper;
import com.ruoyi.system.service.IIcesManufactureTaskService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;

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
        return baseMapper.updateById(update) > 0;
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
}
