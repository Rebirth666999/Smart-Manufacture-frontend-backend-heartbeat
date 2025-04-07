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
import com.ruoyi.system.domain.bo.IcesExceptionSourceBo;
import com.ruoyi.system.domain.vo.IcesExceptionSourceVo;
import com.ruoyi.system.domain.IcesExceptionSource;
import com.ruoyi.system.mapper.IcesExceptionSourceMapper;
import com.ruoyi.system.service.IIcesExceptionSourceService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 异常源Service业务层处理
 *
 * @author ruoyi
 * @date 2025-04-07
 */
@RequiredArgsConstructor
@Service
public class IcesExceptionSourceServiceImpl implements IIcesExceptionSourceService {

    private final IcesExceptionSourceMapper baseMapper;
    private final IIcesCodeService codeService;

    /**
     * 查询异常源
     */
    @Override
    public IcesExceptionSourceVo queryById(Long exsId){
        return baseMapper.selectVoById(exsId);
    }

    /**
     * 查询异常源列表
     */
    @Override
    public TableDataInfo<IcesExceptionSourceVo> queryPageList(IcesExceptionSourceBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesExceptionSource> lqw = buildQueryWrapper(bo);
        Page<IcesExceptionSourceVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询异常源列表
     */
    @Override
    public List<IcesExceptionSourceVo> queryList(IcesExceptionSourceBo bo) {
        LambdaQueryWrapper<IcesExceptionSource> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesExceptionSource> buildQueryWrapper(IcesExceptionSourceBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesExceptionSource> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getExsCode()), IcesExceptionSource::getExsCode, bo.getExsCode());
        lqw.like(StringUtils.isNotBlank(bo.getExsName()), IcesExceptionSource::getExsName, bo.getExsName());
        lqw.eq(bo.getExsDelete() != null, IcesExceptionSource::getExsDelete, bo.getExsDelete());
        return lqw;
    }

    /**
     * 新增异常源
     */
    @Override
    public Boolean insertByBo(IcesExceptionSourceBo bo) {
        bo.setExsCode(codeService.insertByType("ExceptionSource"));
        IcesExceptionSource add = BeanUtil.toBean(bo, IcesExceptionSource.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setExsId(add.getExsId());
        }
        return flag;
    }

    /**
     * 修改异常源
     */
    @Override
    public Boolean updateByBo(IcesExceptionSourceBo bo) {
        IcesExceptionSource update = BeanUtil.toBean(bo, IcesExceptionSource.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesExceptionSource entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除异常源
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
