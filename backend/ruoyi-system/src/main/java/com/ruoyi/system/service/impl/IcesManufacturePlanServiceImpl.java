package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.IcesManufacturePlanBo;
import com.ruoyi.system.domain.vo.IcesManufacturePlanVo;
import com.ruoyi.system.domain.IcesManufacturePlan;
import com.ruoyi.system.mapper.IcesManufacturePlanMapper;
import com.ruoyi.system.service.IIcesManufacturePlanService;

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
        lqw.eq(bo.getOrCode() != null, IcesManufacturePlan::getOrCode, bo.getOrCode());
        lqw.eq(bo.getProcCode() != null, IcesManufacturePlan::getProcCode, bo.getProcCode());
        lqw.eq(StringUtils.isNotBlank(bo.getMpStat()), IcesManufacturePlan::getMpStat, bo.getMpStat());
        lqw.eq(bo.getMpPriority() != null, IcesManufacturePlan::getMpPriority, bo.getMpPriority());
        lqw.eq(bo.getMpDelete() != null, IcesManufacturePlan::getMpDelete, bo.getMpDelete());
        return lqw;
    }

    /**
     * 新增生产计划
     */
    @Override
    public Boolean insertByBo(IcesManufacturePlanBo bo) {
        IcesManufacturePlan add = BeanUtil.toBean(bo, IcesManufacturePlan.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setMpId(add.getMpId());
        }
        return flag;
    }

    /**
     * 修改生产计划
     */
    @Override
    public Boolean updateByBo(IcesManufacturePlanBo bo) {
        IcesManufacturePlan update = BeanUtil.toBean(bo, IcesManufacturePlan.class);
        validEntityBeforeSave(update);
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
}
