package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.service.IIcesCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.IcesProcessStepPrevRoundBo;
import com.ruoyi.system.domain.vo.IcesProcessStepPrevRoundVo;
import com.ruoyi.system.domain.IcesProcessStepPrevRound;
import com.ruoyi.system.mapper.IcesProcessStepPrevRoundMapper;
import com.ruoyi.system.service.IIcesProcessStepPrevRoundService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 关联-工艺步骤的跨轮次前序步骤Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-09
 */
@RequiredArgsConstructor
@Service
public class IcesProcessStepPrevRoundServiceImpl implements IIcesProcessStepPrevRoundService {

    private final IcesProcessStepPrevRoundMapper baseMapper;
    private final IIcesCodeService codeService;

    /**
     * 查询关联-工艺步骤的跨轮次前序步骤
     */
    @Override
    public IcesProcessStepPrevRoundVo queryById(Long psprId){
        return baseMapper.selectVoById(psprId);
    }
    @Override
    public IcesProcessStepPrevRoundVo queryByCurId(Long psIdCur){
        return baseMapper.selectVoById(psIdCur);
    }
    /**
     * 查询关联-工艺步骤的跨轮次前序步骤列表
     */
    @Override
    public TableDataInfo<IcesProcessStepPrevRoundVo> queryPageList(IcesProcessStepPrevRoundBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesProcessStepPrevRound> lqw = buildQueryWrapper(bo);
        Page<IcesProcessStepPrevRoundVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询关联-工艺步骤的跨轮次前序步骤列表
     */
    @Override
    public List<IcesProcessStepPrevRoundVo> queryList(IcesProcessStepPrevRoundBo bo) {
        LambdaQueryWrapper<IcesProcessStepPrevRound> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesProcessStepPrevRound> buildQueryWrapper(IcesProcessStepPrevRoundBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesProcessStepPrevRound> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getPsprCode()), IcesProcessStepPrevRound::getPsprCode, bo.getPsprCode());
        lqw.eq(StringUtils.isNotBlank(bo.getPsCodePrev()), IcesProcessStepPrevRound::getPsCodePrev, bo.getPsCodePrev());
        lqw.eq(StringUtils.isNotBlank(bo.getPsCodeCur()), IcesProcessStepPrevRound::getPsCodeCur, bo.getPsCodeCur());
        lqw.eq(bo.getPsprDelete() != null, IcesProcessStepPrevRound::getPsprDelete, bo.getPsprDelete());
        return lqw;
    }

    /**
     * 新增关联-工艺步骤的跨轮次前序步骤
     */
    @Override
    public Boolean insertByBo(IcesProcessStepPrevRoundBo bo) {
        bo.setPsprCode(codeService.insertByType("ProcessStepPrevRound"));
        IcesProcessStepPrevRound add = BeanUtil.toBean(bo, IcesProcessStepPrevRound.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setPsprId(add.getPsprId());
        }
        return flag;
    }

    /**
     * 修改关联-工艺步骤的跨轮次前序步骤
     */
    @Override
    public Boolean updateByBo(IcesProcessStepPrevRoundBo bo) {
        IcesProcessStepPrevRound update = BeanUtil.toBean(bo, IcesProcessStepPrevRound.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesProcessStepPrevRound entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除关联-工艺步骤的跨轮次前序步骤
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }


}
