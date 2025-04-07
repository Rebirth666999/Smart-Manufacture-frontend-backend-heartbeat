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
import com.ruoyi.system.domain.bo.IcesExceptionBo;
import com.ruoyi.system.domain.vo.IcesExceptionVo;
import com.ruoyi.system.domain.IcesException;
import com.ruoyi.system.mapper.IcesExceptionMapper;
import com.ruoyi.system.service.IIcesExceptionService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 异常Service业务层处理
 *
 * @author ruoyi
 * @date 2025-04-07
 */
@RequiredArgsConstructor
@Service
public class IcesExceptionServiceImpl implements IIcesExceptionService {

    private final IcesExceptionMapper baseMapper;

    /**
     * 查询异常
     */
    @Override
    public IcesExceptionVo queryById(Long exId){
        return baseMapper.selectVoById(exId);
    }

    /**
     * 查询异常列表
     */
    @Override
    public TableDataInfo<IcesExceptionVo> queryPageList(IcesExceptionBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesException> lqw = buildQueryWrapper(bo);
        Page<IcesExceptionVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询异常列表
     */
    @Override
    public List<IcesExceptionVo> queryList(IcesExceptionBo bo) {
        LambdaQueryWrapper<IcesException> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesException> buildQueryWrapper(IcesExceptionBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesException> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getExCode()), IcesException::getExCode, bo.getExCode());
        lqw.eq(StringUtils.isNotBlank(bo.getEtCode()), IcesException::getEtCode, bo.getEtCode());
        lqw.like(StringUtils.isNotBlank(bo.getExName()), IcesException::getExName, bo.getExName());
        lqw.eq(bo.getExDelete() != null, IcesException::getExDelete, bo.getExDelete());
        return lqw;
    }

    /**
     * 新增异常
     */
    @Override
    public Boolean insertByBo(IcesExceptionBo bo) {
        IcesException add = BeanUtil.toBean(bo, IcesException.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setExId(add.getExId());
        }
        return flag;
    }

    /**
     * 修改异常
     */
    @Override
    public Boolean updateByBo(IcesExceptionBo bo) {
        IcesException update = BeanUtil.toBean(bo, IcesException.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesException entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除异常
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
