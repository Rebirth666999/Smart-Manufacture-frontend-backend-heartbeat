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
import com.ruoyi.system.domain.bo.IcesProcessStepPrevBo;
import com.ruoyi.system.domain.vo.IcesProcessStepPrevVo;
import com.ruoyi.system.domain.IcesProcessStepPrev;
import com.ruoyi.system.mapper.IcesProcessStepPrevMapper;
import com.ruoyi.system.service.IIcesProcessStepPrevService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 关联-工艺步骤的前序步骤Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-09
 */
@RequiredArgsConstructor
@Service
public class IcesProcessStepPrevServiceImpl implements IIcesProcessStepPrevService {

    private final IcesProcessStepPrevMapper baseMapper;
    private final IIcesCodeService codeService;

    /**
     * 查询关联-工艺步骤的前序步骤
     */
    @Override
    public IcesProcessStepPrevVo queryById(Long pspId){
        return baseMapper.selectVoById(pspId);
    }
    @Override
    public IcesProcessStepPrevVo queryByCurId(Long pspIdCur){
        return baseMapper.selectVoById(pspIdCur);
    }
    /**
     * 查询关联-工艺步骤的前序步骤列表
     */
    @Override
    public TableDataInfo<IcesProcessStepPrevVo> queryPageList(IcesProcessStepPrevBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesProcessStepPrev> lqw = buildQueryWrapper(bo);
        Page<IcesProcessStepPrevVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询关联-工艺步骤的前序步骤列表
     */
    @Override
    public List<IcesProcessStepPrevVo> queryList(IcesProcessStepPrevBo bo) {
        LambdaQueryWrapper<IcesProcessStepPrev> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesProcessStepPrev> buildQueryWrapper(IcesProcessStepPrevBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesProcessStepPrev> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getPspCode()), IcesProcessStepPrev::getPspCode, bo.getPspCode());
        lqw.eq(StringUtils.isNotBlank(bo.getPsCodePrev()), IcesProcessStepPrev::getPsCodePrev, bo.getPsCodePrev());
        lqw.eq(StringUtils.isNotBlank(bo.getPsCodeCur()), IcesProcessStepPrev::getPsCodeCur, bo.getPsCodeCur());
        lqw.eq(bo.getPspDelete() != null, IcesProcessStepPrev::getPspDelete, bo.getPspDelete());
        return lqw;
    }

    /**
     * 新增关联-工艺步骤的前序步骤
     */
    @Override
    public Boolean insertByBo(IcesProcessStepPrevBo bo) {
        bo.setPspCode(codeService.insertByType("ProcessStepPrev"));
        IcesProcessStepPrev add = BeanUtil.toBean(bo, IcesProcessStepPrev.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setPspId(add.getPspId());
        }
        return flag;
    }

    /**
     * 修改关联-工艺步骤的前序步骤
     */
    @Override
    public Boolean updateByBo(IcesProcessStepPrevBo bo) {
        IcesProcessStepPrev update = BeanUtil.toBean(bo, IcesProcessStepPrev.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesProcessStepPrev entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除关联-工艺步骤的前序步骤
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
