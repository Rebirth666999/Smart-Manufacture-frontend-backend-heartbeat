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
import com.ruoyi.system.domain.bo.IcesClientLevelBo;
import com.ruoyi.system.domain.vo.IcesClientLevelVo;
import com.ruoyi.system.domain.IcesClientLevel;
import com.ruoyi.system.mapper.IcesClientLevelMapper;
import com.ruoyi.system.service.IIcesClientLevelService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 客户等级Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-24
 */
@RequiredArgsConstructor
@Service
public class IcesClientLevelServiceImpl implements IIcesClientLevelService {

    private final IcesClientLevelMapper baseMapper;
    private final IIcesCodeService codeService;

    /**
     * 查询客户等级
     */
    @Override
    public IcesClientLevelVo queryById(Long cllId){
        return baseMapper.selectVoById(cllId);
    }

    /**
     * 查询客户等级列表
     */
    @Override
    public TableDataInfo<IcesClientLevelVo> queryPageList(IcesClientLevelBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesClientLevel> lqw = buildQueryWrapper(bo);
        Page<IcesClientLevelVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询客户等级列表
     */
    @Override
    public List<IcesClientLevelVo> queryList(IcesClientLevelBo bo) {
        LambdaQueryWrapper<IcesClientLevel> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesClientLevel> buildQueryWrapper(IcesClientLevelBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesClientLevel> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getCllCode()), IcesClientLevel::getCllCode, bo.getCllCode());
        lqw.like(StringUtils.isNotBlank(bo.getCllLabel()), IcesClientLevel::getCllLabel, bo.getCllLabel());
        lqw.like(StringUtils.isNotBlank(bo.getCllName()), IcesClientLevel::getCllName, bo.getCllName());
        lqw.eq(bo.getCllDelete() != null, IcesClientLevel::getCllDelete, bo.getCllDelete());
        return lqw;
    }

    /**
     * 新增客户等级
     */
    @Override
    public Boolean insertByBo(IcesClientLevelBo bo) {
        bo.setCllCode(codeService.insertByType("ClientLevel"));
        IcesClientLevel add = BeanUtil.toBean(bo, IcesClientLevel.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setCllId(add.getCllId());
        }
        return flag;
    }

    /**
     * 修改客户等级
     */
    @Override
    public Boolean updateByBo(IcesClientLevelBo bo) {
        IcesClientLevel update = BeanUtil.toBean(bo, IcesClientLevel.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesClientLevel entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除客户等级
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
