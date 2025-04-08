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
import com.ruoyi.system.domain.bo.IcesExceptionParamMapBo;
import com.ruoyi.system.domain.vo.IcesExceptionParamMapVo;
import com.ruoyi.system.domain.IcesExceptionParamMap;
import com.ruoyi.system.mapper.IcesExceptionParamMapMapper;
import com.ruoyi.system.service.IIcesExceptionParamMapService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 异常参数模板条目Service业务层处理
 *
 * @author ruoyi
 * @date 2025-04-08
 */
@RequiredArgsConstructor
@Service
public class IcesExceptionParamMapServiceImpl implements IIcesExceptionParamMapService {

    private final IcesExceptionParamMapMapper baseMapper;
    private final IIcesCodeService codeService;

    /**
     * 查询异常参数模板条目
     */
    @Override
    public IcesExceptionParamMapVo queryById(Long expmId){
        return baseMapper.selectVoById(expmId);
    }

    /**
     * 查询异常参数模板条目列表
     */
    @Override
    public TableDataInfo<IcesExceptionParamMapVo> queryPageList(IcesExceptionParamMapBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesExceptionParamMap> lqw = buildQueryWrapper(bo);
        Page<IcesExceptionParamMapVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询异常参数模板条目列表
     */
    @Override
    public List<IcesExceptionParamMapVo> queryList(IcesExceptionParamMapBo bo) {
        LambdaQueryWrapper<IcesExceptionParamMap> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesExceptionParamMap> buildQueryWrapper(IcesExceptionParamMapBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesExceptionParamMap> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getExpmCode()), IcesExceptionParamMap::getExpmCode, bo.getExpmCode());
        lqw.eq(StringUtils.isNotBlank(bo.getExptCode()), IcesExceptionParamMap::getExptCode, bo.getExptCode());
        lqw.like(StringUtils.isNotBlank(bo.getExpmNameOrgn()), IcesExceptionParamMap::getExpmNameOrgn, bo.getExpmNameOrgn());
        lqw.like(StringUtils.isNotBlank(bo.getExpName()), IcesExceptionParamMap::getExpName, bo.getExpName());
        lqw.eq(bo.getExpmDelete() != null, IcesExceptionParamMap::getExpmDelete, bo.getExpmDelete());
        return lqw;
    }

    /**
     * 新增异常参数模板条目
     */
    @Override
    public Boolean insertByBo(IcesExceptionParamMapBo bo) {
        bo.setExpmCode(codeService.insertByType("ExceptionParamMap"));
        IcesExceptionParamMap add = BeanUtil.toBean(bo, IcesExceptionParamMap.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setExpmId(add.getExpmId());
        }
        return flag;
    }

    /**
     * 修改异常参数模板条目
     */
    @Override
    public Boolean updateByBo(IcesExceptionParamMapBo bo) {
        IcesExceptionParamMap update = BeanUtil.toBean(bo, IcesExceptionParamMap.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesExceptionParamMap entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除异常参数模板条目
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
