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
import com.ruoyi.system.domain.bo.IcesClientTradeBo;
import com.ruoyi.system.domain.vo.IcesClientTradeVo;
import com.ruoyi.system.domain.IcesClientTrade;
import com.ruoyi.system.mapper.IcesClientTradeMapper;
import com.ruoyi.system.service.IIcesClientTradeService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 客户贸易信息Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-28
 */
@RequiredArgsConstructor
@Service
public class IcesClientTradeServiceImpl implements IIcesClientTradeService {

    private final IcesClientTradeMapper baseMapper;
    private final IIcesCodeService codeService;

    /**
     * 查询客户贸易信息
     */
    @Override
    public IcesClientTradeVo queryById(Long ctId){
        return baseMapper.selectVoById(ctId);
    }

    /**
     * 查询客户贸易信息列表
     */
    @Override
    public TableDataInfo<IcesClientTradeVo> queryPageList(IcesClientTradeBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesClientTrade> lqw = buildQueryWrapper(bo);
        Page<IcesClientTradeVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询客户贸易信息列表
     */
    @Override
    public List<IcesClientTradeVo> queryList(IcesClientTradeBo bo) {
        LambdaQueryWrapper<IcesClientTrade> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesClientTrade> buildQueryWrapper(IcesClientTradeBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesClientTrade> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getCtCode()), IcesClientTrade::getCtCode, bo.getCtCode());
        lqw.eq(StringUtils.isNotBlank(bo.getClCode()), IcesClientTrade::getClCode, bo.getClCode());
        lqw.eq(bo.getCtDelete() != null, IcesClientTrade::getCtDelete, bo.getCtDelete());
        return lqw;
    }

    /**
     * 新增客户贸易信息
     */
    @Override
    public Boolean insertByBo(IcesClientTradeBo bo) {
        bo.setCtCode(codeService.insertByType("ClientTrade"));
        IcesClientTrade add = BeanUtil.toBean(bo, IcesClientTrade.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setCtId(add.getCtId());
        }
        return flag;
    }

    /**
     * 修改客户贸易信息
     */
    @Override
    public Boolean updateByBo(IcesClientTradeBo bo) {
        IcesClientTrade update = BeanUtil.toBean(bo, IcesClientTrade.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesClientTrade entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除客户贸易信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
