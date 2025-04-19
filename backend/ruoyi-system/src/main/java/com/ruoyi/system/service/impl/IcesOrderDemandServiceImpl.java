package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.service.IIcesCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.IcesOrderDemandBo;
import com.ruoyi.system.domain.vo.IcesOrderDemandVo;
import com.ruoyi.system.domain.IcesOrderDemand;
import com.ruoyi.system.mapper.IcesOrderDemandMapper;
import com.ruoyi.system.service.IIcesOrderDemandService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 订单所需产品关联Service业务层处理
 *
 * @author ruoyi
 * @date 2025-04-10
 */
@RequiredArgsConstructor
@Service
public class IcesOrderDemandServiceImpl implements IIcesOrderDemandService {

    private final IcesOrderDemandMapper baseMapper;
    private final IIcesCodeService codeService;

    /**
     * 查询订单所需产品关联
     */
    @Override
    public IcesOrderDemandVo queryById(Long odId){
        return baseMapper.selectVoById(odId);
    }

    /**
     * 查询订单所需产品关联列表
     */
    @Override
    public TableDataInfo<IcesOrderDemandVo> queryPageList(IcesOrderDemandBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesOrderDemand> lqw = buildQueryWrapper(bo);
        Page<IcesOrderDemandVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询订单所需产品关联列表
     */
    @Override
    public List<IcesOrderDemandVo> queryList(IcesOrderDemandBo bo) {
        LambdaQueryWrapper<IcesOrderDemand> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesOrderDemand> buildQueryWrapper(IcesOrderDemandBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesOrderDemand> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getOdCode()), IcesOrderDemand::getOdCode, bo.getOdCode());
        lqw.eq(StringUtils.isNotBlank(bo.getPrCode()), IcesOrderDemand::getPrCode, bo.getPrCode());
        lqw.eq(StringUtils.isNotBlank(bo.getOrCode()), IcesOrderDemand::getOrCode, bo.getOrCode());
        lqw.eq(bo.getOdDemand() != null, IcesOrderDemand::getOdDemand, bo.getOdDemand());
        lqw.eq(bo.getOdDelete() != null, IcesOrderDemand::getOdDelete, bo.getOdDelete());
        return lqw;
    }

    /**
     * 新增订单所需产品关联
     */
    @Override
    public Boolean insertByBo(IcesOrderDemandBo bo) {
        bo.setOdCode(codeService.insertByType("OrderDemand"));
        IcesOrderDemand add = BeanUtil.toBean(bo, IcesOrderDemand.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setOdId(add.getOdId());
        }
        return flag;
    }

    /**
     * 修改订单所需产品关联
     */
    @Override
    public Boolean updateByBo(IcesOrderDemandBo bo) {
        IcesOrderDemand update = BeanUtil.toBean(bo, IcesOrderDemand.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesOrderDemand entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除订单所需产品关联
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
