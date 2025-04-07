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
import com.ruoyi.system.domain.bo.IcesExceptionParamTemplateBo;
import com.ruoyi.system.domain.vo.IcesExceptionParamTemplateVo;
import com.ruoyi.system.domain.IcesExceptionParamTemplate;
import com.ruoyi.system.mapper.IcesExceptionParamTemplateMapper;
import com.ruoyi.system.service.IIcesExceptionParamTemplateService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 异常参数模板Service业务层处理
 *
 * @author ruoyi
 * @date 2025-04-07
 */
@RequiredArgsConstructor
@Service
public class IcesExceptionParamTemplateServiceImpl implements IIcesExceptionParamTemplateService {

    private final IcesExceptionParamTemplateMapper baseMapper;

    /**
     * 查询异常参数模板
     */
    @Override
    public IcesExceptionParamTemplateVo queryById(Long exptId){
        return baseMapper.selectVoById(exptId);
    }

    /**
     * 查询异常参数模板列表
     */
    @Override
    public TableDataInfo<IcesExceptionParamTemplateVo> queryPageList(IcesExceptionParamTemplateBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesExceptionParamTemplate> lqw = buildQueryWrapper(bo);
        Page<IcesExceptionParamTemplateVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询异常参数模板列表
     */
    @Override
    public List<IcesExceptionParamTemplateVo> queryList(IcesExceptionParamTemplateBo bo) {
        LambdaQueryWrapper<IcesExceptionParamTemplate> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesExceptionParamTemplate> buildQueryWrapper(IcesExceptionParamTemplateBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesExceptionParamTemplate> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getExptCode()), IcesExceptionParamTemplate::getExptCode, bo.getExptCode());
        lqw.eq(StringUtils.isNotBlank(bo.getExCode()), IcesExceptionParamTemplate::getExCode, bo.getExCode());
        lqw.eq(StringUtils.isNotBlank(bo.getExsCode()), IcesExceptionParamTemplate::getExsCode, bo.getExsCode());
        lqw.eq(bo.getExptDelete() != null, IcesExceptionParamTemplate::getExptDelete, bo.getExptDelete());
        return lqw;
    }

    /**
     * 新增异常参数模板
     */
    @Override
    public Boolean insertByBo(IcesExceptionParamTemplateBo bo) {
        IcesExceptionParamTemplate add = BeanUtil.toBean(bo, IcesExceptionParamTemplate.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setExptId(add.getExptId());
        }
        return flag;
    }

    /**
     * 修改异常参数模板
     */
    @Override
    public Boolean updateByBo(IcesExceptionParamTemplateBo bo) {
        IcesExceptionParamTemplate update = BeanUtil.toBean(bo, IcesExceptionParamTemplate.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesExceptionParamTemplate entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除异常参数模板
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
