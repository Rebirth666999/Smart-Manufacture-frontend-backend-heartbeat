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
import com.ruoyi.system.domain.bo.IcesExceptionParamBo;
import com.ruoyi.system.domain.vo.IcesExceptionParamVo;
import com.ruoyi.system.domain.IcesExceptionParam;
import com.ruoyi.system.mapper.IcesExceptionParamMapper;
import com.ruoyi.system.service.IIcesExceptionParamService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 异常参数Service业务层处理
 *
 * @author ruoyi
 * @date 2025-04-07
 */
@RequiredArgsConstructor
@Service
public class IcesExceptionParamServiceImpl implements IIcesExceptionParamService {

    private final IcesExceptionParamMapper baseMapper;

    /**
     * 查询异常参数
     */
    @Override
    public IcesExceptionParamVo queryById(Long expId){
        return baseMapper.selectVoById(expId);
    }

    /**
     * 查询异常参数列表
     */
    @Override
    public TableDataInfo<IcesExceptionParamVo> queryPageList(IcesExceptionParamBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesExceptionParam> lqw = buildQueryWrapper(bo);
        Page<IcesExceptionParamVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询异常参数列表
     */
    @Override
    public List<IcesExceptionParamVo> queryList(IcesExceptionParamBo bo) {
        LambdaQueryWrapper<IcesExceptionParam> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesExceptionParam> buildQueryWrapper(IcesExceptionParamBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesExceptionParam> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getExpCode()), IcesExceptionParam::getExpCode, bo.getExpCode());
        lqw.eq(StringUtils.isNotBlank(bo.getExCode()), IcesExceptionParam::getExCode, bo.getExCode());
        lqw.like(StringUtils.isNotBlank(bo.getExpName()), IcesExceptionParam::getExpName, bo.getExpName());
        lqw.eq(StringUtils.isNotBlank(bo.getExpType()), IcesExceptionParam::getExpType, bo.getExpType());
        lqw.eq(bo.getExpDelete() != null, IcesExceptionParam::getExpDelete, bo.getExpDelete());
        return lqw;
    }

    /**
     * 新增异常参数
     */
    @Override
    public Boolean insertByBo(IcesExceptionParamBo bo) {
        IcesExceptionParam add = BeanUtil.toBean(bo, IcesExceptionParam.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setExpId(add.getExpId());
        }
        return flag;
    }

    /**
     * 修改异常参数
     */
    @Override
    public Boolean updateByBo(IcesExceptionParamBo bo) {
        IcesExceptionParam update = BeanUtil.toBean(bo, IcesExceptionParam.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesExceptionParam entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除异常参数
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
