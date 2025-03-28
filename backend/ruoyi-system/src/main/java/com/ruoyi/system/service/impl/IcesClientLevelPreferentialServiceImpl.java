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
import com.ruoyi.system.domain.bo.IcesClientLevelPreferentialBo;
import com.ruoyi.system.domain.vo.IcesClientLevelPreferentialVo;
import com.ruoyi.system.domain.IcesClientLevelPreferential;
import com.ruoyi.system.mapper.IcesClientLevelPreferentialMapper;
import com.ruoyi.system.service.IIcesClientLevelPreferentialService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 关联-客户等级对应的优惠策略Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-27
 */
@RequiredArgsConstructor
@Service
public class IcesClientLevelPreferentialServiceImpl implements IIcesClientLevelPreferentialService {

    private final IcesClientLevelPreferentialMapper baseMapper;
    private final IIcesCodeService codeService;

    /**
     * 查询关联-客户等级对应的优惠策略
     */
    @Override
    public IcesClientLevelPreferentialVo queryById(Long clpId){
        return baseMapper.selectVoById(clpId);
    }

    /**
     * 查询关联-客户等级对应的优惠策略列表
     */
    @Override
    public TableDataInfo<IcesClientLevelPreferentialVo> queryPageList(IcesClientLevelPreferentialBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesClientLevelPreferential> lqw = buildQueryWrapper(bo);
        Page<IcesClientLevelPreferentialVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询关联-客户等级对应的优惠策略列表
     */
    @Override
    public List<IcesClientLevelPreferentialVo> queryList(IcesClientLevelPreferentialBo bo) {
        LambdaQueryWrapper<IcesClientLevelPreferential> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesClientLevelPreferential> buildQueryWrapper(IcesClientLevelPreferentialBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesClientLevelPreferential> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getClpCode()), IcesClientLevelPreferential::getClpCode, bo.getClpCode());
        lqw.eq(StringUtils.isNotBlank(bo.getCllCode()), IcesClientLevelPreferential::getCllCode, bo.getCllCode());
        lqw.eq(StringUtils.isNotBlank(bo.getCpCode()), IcesClientLevelPreferential::getCpCode, bo.getCpCode());
        lqw.eq(bo.getClpDelete() != null, IcesClientLevelPreferential::getClpDelete, bo.getClpDelete());
        return lqw;
    }

    /**
     * 新增关联-客户等级对应的优惠策略
     */
    @Override
    public Boolean insertByBo(IcesClientLevelPreferentialBo bo) {
        bo.setClpCode(codeService.insertByType("ClientLevelPreferential"));
        IcesClientLevelPreferential add = BeanUtil.toBean(bo, IcesClientLevelPreferential.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setClpId(add.getClpId());
        }
        return flag;
    }

    /**
     * 修改关联-客户等级对应的优惠策略
     */
    @Override
    public Boolean updateByBo(IcesClientLevelPreferentialBo bo) {
        IcesClientLevelPreferential update = BeanUtil.toBean(bo, IcesClientLevelPreferential.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesClientLevelPreferential entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除关联-客户等级对应的优惠策略
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
