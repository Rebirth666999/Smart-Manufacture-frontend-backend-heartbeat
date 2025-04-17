package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.domain.bo.IcesManufactureTaskBo;
import com.ruoyi.system.service.IIcesCodeService;
import com.ruoyi.system.service.IIcesOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.IcesManufacturePlanBo;
import com.ruoyi.system.domain.vo.IcesManufacturePlanVo;
import com.ruoyi.system.domain.IcesManufacturePlan;
import com.ruoyi.system.mapper.IcesManufacturePlanMapper;
import com.ruoyi.system.service.IIcesManufacturePlanService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 生产计划Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-17
 */
@RequiredArgsConstructor
@Service
public class IcesManufacturePlanServiceImpl implements IIcesManufacturePlanService {

    private final IcesManufacturePlanMapper baseMapper;
    private final IIcesCodeService codeService;
    private final IIcesOrderService orderService;

    /**
     * 查询生产计划
     */
    @Override
    public IcesManufacturePlanVo queryById(Long mpId){
        return baseMapper.selectVoById(mpId);
    }

    /**
     * 查询生产计划列表
     */
    @Override
    public TableDataInfo<IcesManufacturePlanVo> queryPageList(IcesManufacturePlanBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesManufacturePlan> lqw = buildQueryWrapper(bo);
        Page<IcesManufacturePlanVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    @Override
    public TableDataInfo<IcesManufacturePlanVo> queryReviewList(IcesManufacturePlanBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesManufacturePlan> lqw = buildQueryWrapper(bo);
        List<String> stats = new ArrayList<>();
        stats.add("2");  // 待审核
        stats.add("3");  // 审核中
        stats.add("a");  // 待审核（弃用）
        stats.add("b");  // 审核中（弃用）
        stats.add("7");  // 待审核（修改）
        stats.add("8");  // 待审核（修改）
        lqw.in(IcesManufacturePlan::getMpStat, stats);
        Page<IcesManufacturePlanVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询生产计划列表
     */
    @Override
    public List<IcesManufacturePlanVo> queryList(IcesManufacturePlanBo bo) {
        LambdaQueryWrapper<IcesManufacturePlan> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesManufacturePlan> buildQueryWrapper(IcesManufacturePlanBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesManufacturePlan> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getMpCode()), IcesManufacturePlan::getMpCode, bo.getMpCode());
        lqw.eq(StringUtils.isNotBlank(bo.getOrCode()), IcesManufacturePlan::getOrCode, bo.getOrCode());
        lqw.eq(StringUtils.isNotBlank(bo.getOdCode()), IcesManufacturePlan::getOrCode, bo.getOdCode());
        lqw.eq(StringUtils.isNotBlank(bo.getMpStat()), IcesManufacturePlan::getMpStat, bo.getMpStat());
        lqw.eq(bo.getMpPriority() != null, IcesManufacturePlan::getMpPriority, bo.getMpPriority());
        lqw.eq(bo.getMpDelete() != null, IcesManufacturePlan::getMpDelete, bo.getMpDelete());
        return lqw;
    }

    /**
     * 新增生产计划
     */
    @Override
    public IcesManufacturePlanVo insertByBo(IcesManufacturePlanBo bo) {
        bo.setMpCode(codeService.insertByType("ManufacturePlan"));
        IcesManufacturePlan add = BeanUtil.toBean(bo, IcesManufacturePlan.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setMpId(add.getMpId());
        }
        return queryById(add.getMpId());
    }

    /**
     * 修改生产计划
     */
    @Override
    public Boolean updateByBo(IcesManufacturePlanBo bo) {
        IcesManufacturePlan update = BeanUtil.toBean(bo, IcesManufacturePlan.class);
        validEntityBeforeSave(update);
        //判断状态是否为已完成
        if (bo.getMpStat().equals("6")) {
            IcesManufacturePlanBo icesManufacturePlanBo = new IcesManufacturePlanBo();
            icesManufacturePlanBo.setMpCode(update.getMpCode());
            List<IcesManufacturePlanVo> OtherVos = queryList(icesManufacturePlanBo);
            int done = 1;
            for (IcesManufacturePlanVo otherVo : OtherVos) {
                if(!otherVo.getMpStat().equals("6")) {
                    //证明还有未完成
                    done = 0;
                    break;
                }
            }
            if (done == 1)
            {
                //所有的都已完成
                orderService.updateStatus(bo);
            }
        }
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesManufacturePlan entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除生产计划
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
    @Override
    public void updateStatus(IcesManufactureTaskBo icesManufactureTaskBo) {
        IcesManufacturePlanBo bo = new IcesManufacturePlanBo();
        bo.setMpCode(icesManufactureTaskBo.getMtCode());
        List<IcesManufacturePlanVo> icesManufacturePlanVos = queryList(bo);
        bo.setMpId(icesManufacturePlanVos.get(0).getMpId());
        updateByBo(bo);
    }

}
