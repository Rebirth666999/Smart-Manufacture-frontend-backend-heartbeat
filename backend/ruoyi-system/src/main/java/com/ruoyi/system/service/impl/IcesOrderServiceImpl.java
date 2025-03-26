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
import com.ruoyi.system.domain.bo.IcesOrderBo;
import com.ruoyi.system.domain.vo.IcesOrderVo;
import com.ruoyi.system.domain.IcesOrder;
import com.ruoyi.system.mapper.IcesOrderMapper;
import com.ruoyi.system.service.IIcesOrderService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 订单Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-17
 */
@RequiredArgsConstructor
@Service
public class IcesOrderServiceImpl implements IIcesOrderService {

    private final IcesOrderMapper baseMapper;

    /**
     * 查询订单
     */
    @Override
    public IcesOrderVo queryById(Long orId){
        return baseMapper.selectVoById(orId);
    }

    /**
     * 查询订单列表
     */
    @Override
    public TableDataInfo<IcesOrderVo> queryPageList(IcesOrderBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesOrder> lqw = buildQueryWrapper(bo);
        Page<IcesOrderVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询订单列表
     */
    @Override
    public List<IcesOrderVo> queryList(IcesOrderBo bo) {
        LambdaQueryWrapper<IcesOrder> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesOrder> buildQueryWrapper(IcesOrderBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getMaCode() != null, IcesOrder::getMaCode, bo.getMaCode());
        lqw.eq(bo.getClCode() != null, IcesOrder::getClCode, bo.getClCode());
        lqw.like(StringUtils.isNotBlank(bo.getOrName()), IcesOrder::getOrName, bo.getOrName());
        lqw.eq(StringUtils.isNotBlank(bo.getOrStat()), IcesOrder::getOrStat, bo.getOrStat());
        lqw.eq(bo.getOrPriority() != null, IcesOrder::getOrPriority, bo.getOrPriority());
        lqw.between(params.get("beginOrDeadline") != null && params.get("endOrDeadline") != null,
            IcesOrder::getOrDeadline ,params.get("beginOrDeadline"), params.get("endOrDeadline"));
        lqw.eq(bo.getOrDelete() != null, IcesOrder::getOrDelete, bo.getOrDelete());
        return lqw;
    }

    /**
     * 新增订单
     */
    @Override
    public Boolean insertByBo(IcesOrderBo bo) {
        IcesOrder add = BeanUtil.toBean(bo, IcesOrder.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setOrId(add.getOrId());
        }
        return flag;
    }

    /**
     * 修改订单
     */
    @Override
    public Boolean updateByBo(IcesOrderBo bo) {
        IcesOrder update = BeanUtil.toBean(bo, IcesOrder.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesOrder entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除订单
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
