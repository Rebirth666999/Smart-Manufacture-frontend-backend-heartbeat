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
import com.ruoyi.system.domain.bo.IcesExceptionLifecycleVersionBo;
import com.ruoyi.system.domain.vo.IcesExceptionLifecycleVersionVo;
import com.ruoyi.system.domain.IcesExceptionLifecycleVersion;
import com.ruoyi.system.mapper.IcesExceptionLifecycleVersionMapper;
import com.ruoyi.system.service.IIcesExceptionLifecycleVersionService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 异常生命周期版本Service业务层处理
 *
 * @author ruoyi
 * @date 2025-04-08
 */
@RequiredArgsConstructor
@Service
public class IcesExceptionLifecycleVersionServiceImpl implements IIcesExceptionLifecycleVersionService {

    private final IcesExceptionLifecycleVersionMapper baseMapper;
    private final IIcesCodeService codeService;

    /**
     * 查询异常生命周期版本
     */
    @Override
    public IcesExceptionLifecycleVersionVo queryById(Long exlvId){
        return baseMapper.selectVoById(exlvId);
    }

    /**
     * 查询异常生命周期版本列表
     */
    @Override
    public TableDataInfo<IcesExceptionLifecycleVersionVo> queryPageList(IcesExceptionLifecycleVersionBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesExceptionLifecycleVersion> lqw = buildQueryWrapper(bo);
        Page<IcesExceptionLifecycleVersionVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询异常生命周期版本列表
     */
    @Override
    public List<IcesExceptionLifecycleVersionVo> queryList(IcesExceptionLifecycleVersionBo bo) {
        LambdaQueryWrapper<IcesExceptionLifecycleVersion> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesExceptionLifecycleVersion> buildQueryWrapper(IcesExceptionLifecycleVersionBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesExceptionLifecycleVersion> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getExlvCode()), IcesExceptionLifecycleVersion::getExlvCode, bo.getExlvCode());
        lqw.eq(StringUtils.isNotBlank(bo.getExlCode()), IcesExceptionLifecycleVersion::getExlCode, bo.getExlCode());
        lqw.like(StringUtils.isNotBlank(bo.getExlvName()), IcesExceptionLifecycleVersion::getExlvName, bo.getExlvName());
        lqw.eq(bo.getExlvDelete() != null, IcesExceptionLifecycleVersion::getExlvDelete, bo.getExlvDelete());
        return lqw;
    }

    /**
     * 新增异常生命周期版本
     */
    @Override
    public Boolean insertByBo(IcesExceptionLifecycleVersionBo bo) {
        bo.setExlvCode(codeService.insertByType("ExceptionLifecycleVersion"));
        IcesExceptionLifecycleVersion add = BeanUtil.toBean(bo, IcesExceptionLifecycleVersion.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setExlvId(add.getExlvId());
        }
        return flag;
    }

    /**
     * 修改异常生命周期版本
     */
    @Override
    public Boolean updateByBo(IcesExceptionLifecycleVersionBo bo) {
        IcesExceptionLifecycleVersion update = BeanUtil.toBean(bo, IcesExceptionLifecycleVersion.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesExceptionLifecycleVersion entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除异常生命周期版本
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
