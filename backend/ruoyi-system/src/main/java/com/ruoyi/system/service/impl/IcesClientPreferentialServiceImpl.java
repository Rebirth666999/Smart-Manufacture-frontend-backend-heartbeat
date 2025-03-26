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
import com.ruoyi.system.domain.bo.IcesClientPreferentialBo;
import com.ruoyi.system.domain.vo.IcesClientPreferentialVo;
import com.ruoyi.system.domain.IcesClientPreferential;
import com.ruoyi.system.mapper.IcesClientPreferentialMapper;
import com.ruoyi.system.service.IIcesClientPreferentialService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 客户优惠策略Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-24
 */
@RequiredArgsConstructor
@Service
public class IcesClientPreferentialServiceImpl implements IIcesClientPreferentialService {

    private final IcesClientPreferentialMapper baseMapper;
    private final IIcesCodeService codeService;

    /**
     * 查询客户优惠策略
     */
    @Override
    public IcesClientPreferentialVo queryById(Long cpId){
        return baseMapper.selectVoById(cpId);
    }

    /**
     * 查询客户优惠策略列表
     */
    @Override
    public TableDataInfo<IcesClientPreferentialVo> queryPageList(IcesClientPreferentialBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesClientPreferential> lqw = buildQueryWrapper(bo);
        Page<IcesClientPreferentialVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询客户优惠策略列表
     */
    @Override
    public List<IcesClientPreferentialVo> queryList(IcesClientPreferentialBo bo) {
        LambdaQueryWrapper<IcesClientPreferential> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesClientPreferential> buildQueryWrapper(IcesClientPreferentialBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesClientPreferential> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getCpCode()), IcesClientPreferential::getCpCode, bo.getCpCode());
        lqw.like(StringUtils.isNotBlank(bo.getCpName()), IcesClientPreferential::getCpName, bo.getCpName());
        lqw.like(StringUtils.isNotBlank(bo.getCpType()), IcesClientPreferential::getCpType, bo.getCpType());
        lqw.eq(bo.getCpDelete() != null, IcesClientPreferential::getCpDelete, bo.getCpDelete());
        return lqw;
    }

    /**
     * 新增客户优惠策略
     */
    @Override
    public Boolean insertByBo(IcesClientPreferentialBo bo) {
        bo.setCpCode(codeService.insertByType("ClientPreferential"));
        IcesClientPreferential add = BeanUtil.toBean(bo, IcesClientPreferential.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setCpId(add.getCpId());
        }
        return flag;
    }

    /**
     * 修改客户优惠策略
     */
    @Override
    public Boolean updateByBo(IcesClientPreferentialBo bo) {
        IcesClientPreferential update = BeanUtil.toBean(bo, IcesClientPreferential.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesClientPreferential entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除客户优惠策略
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
