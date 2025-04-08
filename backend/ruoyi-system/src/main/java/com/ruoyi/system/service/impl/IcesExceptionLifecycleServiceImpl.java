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
import com.ruoyi.system.domain.bo.IcesExceptionLifecycleBo;
import com.ruoyi.system.domain.vo.IcesExceptionLifecycleVo;
import com.ruoyi.system.domain.IcesExceptionLifecycle;
import com.ruoyi.system.mapper.IcesExceptionLifecycleMapper;
import com.ruoyi.system.service.IIcesExceptionLifecycleService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 异常生命周期Service业务层处理
 *
 * @author ruoyi
 * @date 2025-04-08
 */
@RequiredArgsConstructor
@Service
public class IcesExceptionLifecycleServiceImpl implements IIcesExceptionLifecycleService {

    private final IcesExceptionLifecycleMapper baseMapper;

    /**
     * 查询异常生命周期
     */
    @Override
    public IcesExceptionLifecycleVo queryById(Long exlId){
        return baseMapper.selectVoById(exlId);
    }

    /**
     * 查询异常生命周期列表
     */
    @Override
    public TableDataInfo<IcesExceptionLifecycleVo> queryPageList(IcesExceptionLifecycleBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesExceptionLifecycle> lqw = buildQueryWrapper(bo);
        Page<IcesExceptionLifecycleVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询异常生命周期列表
     */
    @Override
    public List<IcesExceptionLifecycleVo> queryList(IcesExceptionLifecycleBo bo) {
        LambdaQueryWrapper<IcesExceptionLifecycle> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesExceptionLifecycle> buildQueryWrapper(IcesExceptionLifecycleBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesExceptionLifecycle> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getExlCode()), IcesExceptionLifecycle::getExlCode, bo.getExlCode());
        lqw.eq(StringUtils.isNotBlank(bo.getExCode()), IcesExceptionLifecycle::getExCode, bo.getExCode());
        lqw.eq(bo.getExlDelete() != null, IcesExceptionLifecycle::getExlDelete, bo.getExlDelete());
        return lqw;
    }

    /**
     * 新增异常生命周期
     */
    @Override
    public Boolean insertByBo(IcesExceptionLifecycleBo bo) {
        IcesExceptionLifecycle add = BeanUtil.toBean(bo, IcesExceptionLifecycle.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setExlId(add.getExlId());
        }
        return flag;
    }

    /**
     * 修改异常生命周期
     */
    @Override
    public Boolean updateByBo(IcesExceptionLifecycleBo bo) {
        IcesExceptionLifecycle update = BeanUtil.toBean(bo, IcesExceptionLifecycle.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesExceptionLifecycle entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除异常生命周期
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
