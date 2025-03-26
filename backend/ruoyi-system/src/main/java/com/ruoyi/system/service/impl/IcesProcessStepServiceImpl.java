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
import com.ruoyi.system.domain.bo.IcesProcessStepBo;
import com.ruoyi.system.domain.vo.IcesProcessStepVo;
import com.ruoyi.system.domain.IcesProcessStep;
import com.ruoyi.system.mapper.IcesProcessStepMapper;
import com.ruoyi.system.service.IIcesProcessStepService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 工艺步骤Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-09
 */
@RequiredArgsConstructor
@Service
public class IcesProcessStepServiceImpl implements IIcesProcessStepService {

    private final IcesProcessStepMapper baseMapper;

    /**
     * 查询工艺步骤
     */
    @Override
    public IcesProcessStepVo queryById(Long psId){
        return baseMapper.selectVoById(psId);
    }

    /**
     * 查询工艺步骤列表
     */
    @Override
    public TableDataInfo<IcesProcessStepVo> queryPageList(IcesProcessStepBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesProcessStep> lqw = buildQueryWrapper(bo);
        Page<IcesProcessStepVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询工艺步骤列表
     */
    @Override
    public List<IcesProcessStepVo> queryList(IcesProcessStepBo bo) {
        LambdaQueryWrapper<IcesProcessStep> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesProcessStep> buildQueryWrapper(IcesProcessStepBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesProcessStep> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getProcCode() != null, IcesProcessStep::getProcCode, bo.getProcCode());
        lqw.eq(bo.getMoCode() != null, IcesProcessStep::getMoCode, bo.getMoCode());
        lqw.eq(bo.getPsDelete() != null, IcesProcessStep::getPsDelete, bo.getPsDelete());
        return lqw;
    }

    /**
     * 新增工艺步骤
     */
    @Override
    public Boolean insertByBo(IcesProcessStepBo bo) {
        IcesProcessStep add = BeanUtil.toBean(bo, IcesProcessStep.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setPsId(add.getPsId());
        }
        return flag;
    }

    /**
     * 修改工艺步骤
     */
    @Override
    public Boolean updateByBo(IcesProcessStepBo bo) {
        IcesProcessStep update = BeanUtil.toBean(bo, IcesProcessStep.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesProcessStep entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除工艺步骤
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
