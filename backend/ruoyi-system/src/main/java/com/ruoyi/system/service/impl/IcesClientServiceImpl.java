package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.domain.vo.IcesManufacturePlanVo;
import com.ruoyi.system.service.IIcesCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.IcesClientBo;
import com.ruoyi.system.domain.vo.IcesClientVo;
import com.ruoyi.system.domain.IcesClient;
import com.ruoyi.system.mapper.IcesClientMapper;
import com.ruoyi.system.service.IIcesClientService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 客户基本信息Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-28
 */
@RequiredArgsConstructor
@Service
public class IcesClientServiceImpl implements IIcesClientService {

    private final IcesClientMapper baseMapper;
    private final IIcesCodeService codeService;

    /**
     * 查询客户基本信息
     */
    @Override
    public IcesClientVo queryById(Long clId){
        return baseMapper.selectVoById(clId);
    }

    /**
     * 查询客户基本信息列表
     */
    @Override
    public TableDataInfo<IcesClientVo> queryPageList(IcesClientBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesClient> lqw = buildQueryWrapper(bo);
        Page<IcesClientVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询客户基本信息列表
     */
    @Override
    public List<IcesClientVo> queryList(IcesClientBo bo) {
        LambdaQueryWrapper<IcesClient> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesClient> buildQueryWrapper(IcesClientBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesClient> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getCllCode()), IcesClient::getCllCode, bo.getCllCode());
        lqw.eq(bo.getClOperator() != null, IcesClient::getClOperator, bo.getClOperator());
        lqw.like(StringUtils.isNotBlank(bo.getClName()), IcesClient::getClName, bo.getClName());
        lqw.eq(StringUtils.isNotBlank(bo.getClStat()), IcesClient::getClStat, bo.getClStat());
        lqw.eq(bo.getClDelete() != null, IcesClient::getClDelete, bo.getClDelete());
        return lqw;
    }

    /**
     * 新增客户基本信息
     */
    @Override
    public IcesClientVo insertByBo(IcesClientBo bo) {
        bo.setClCode(codeService.insertByType("Client"));
        IcesClient add = BeanUtil.toBean(bo, IcesClient.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setClId(add.getClId());
        }
        return queryById(add.getClId());
    }

    /**
     * 修改客户基本信息
     */
    @Override
    public Boolean updateByBo(IcesClientBo bo) {
        IcesClient update = BeanUtil.toBean(bo, IcesClient.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesClient entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除客户基本信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
