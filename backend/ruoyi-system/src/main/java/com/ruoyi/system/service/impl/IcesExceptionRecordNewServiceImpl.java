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
import com.ruoyi.system.domain.bo.IcesExceptionRecordNewBo;
import com.ruoyi.system.domain.vo.IcesExceptionRecordNewVo;
import com.ruoyi.system.domain.IcesExceptionRecordNew;
import com.ruoyi.system.mapper.IcesExceptionRecordNewMapper;
import com.ruoyi.system.service.IIcesExceptionRecordNewService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 异常记录（新）Service业务层处理
 *
 * @author ruoyi
 * @date 2025-07-30
 */
@RequiredArgsConstructor
@Service
public class IcesExceptionRecordNewServiceImpl implements IIcesExceptionRecordNewService {

    private final IcesExceptionRecordNewMapper baseMapper;

    /**
     * 查询异常记录（新）
     */
    @Override
    public IcesExceptionRecordNewVo queryById(Long exrId){
        return baseMapper.selectVoById(exrId);
    }

    /**
     * 查询异常记录（新）列表
     */
    @Override
    public TableDataInfo<IcesExceptionRecordNewVo> queryPageList(IcesExceptionRecordNewBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesExceptionRecordNew> lqw = buildQueryWrapper(bo);
        Page<IcesExceptionRecordNewVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询异常记录（新）列表
     */
    @Override
    public List<IcesExceptionRecordNewVo> queryList(IcesExceptionRecordNewBo bo) {
        LambdaQueryWrapper<IcesExceptionRecordNew> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesExceptionRecordNew> buildQueryWrapper(IcesExceptionRecordNewBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesExceptionRecordNew> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getExrCode()), IcesExceptionRecordNew::getExrCode, bo.getExrCode());
        lqw.eq(StringUtils.isNotBlank(bo.getExsCode()), IcesExceptionRecordNew::getExsCode, bo.getExsCode());
        lqw.eq(StringUtils.isNotBlank(bo.getExCode()), IcesExceptionRecordNew::getExCode, bo.getExCode());
        lqw.eq(StringUtils.isNotBlank(bo.getExrStat()), IcesExceptionRecordNew::getExrStat, bo.getExrStat());
        lqw.eq(StringUtils.isNotBlank(bo.getExrLevel()), IcesExceptionRecordNew::getExrLevel, bo.getExrLevel());
        lqw.between(params.get("beginExrCdate") != null && params.get("endExrCdate") != null,
            IcesExceptionRecordNew::getExrCdate ,params.get("beginExrCdate"), params.get("endExrCdate"));
        lqw.eq(StringUtils.isNotBlank(bo.getExrHumCls()), IcesExceptionRecordNew::getExrHumCls, bo.getExrHumCls());
        lqw.eq(bo.getExrDelete() != null, IcesExceptionRecordNew::getExrDelete, bo.getExrDelete());
        return lqw;
    }

    /**
     * 新增异常记录（新）
     */
    @Override
    public Boolean insertByBo(IcesExceptionRecordNewBo bo) {
        IcesExceptionRecordNew add = BeanUtil.toBean(bo, IcesExceptionRecordNew.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setExrId(add.getExrId());
        }
        return flag;
    }

    /**
     * 修改异常记录（新）
     */
    @Override
    public Boolean updateByBo(IcesExceptionRecordNewBo bo) {
        IcesExceptionRecordNew update = BeanUtil.toBean(bo, IcesExceptionRecordNew.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesExceptionRecordNew entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除异常记录（新）
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
