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
import com.ruoyi.system.domain.bo.IcesEquipmentOperationStepBo;
import com.ruoyi.system.domain.vo.IcesEquipmentOperationStepVo;
import com.ruoyi.system.domain.IcesEquipmentOperationStep;
import com.ruoyi.system.mapper.IcesEquipmentOperationStepMapper;
import com.ruoyi.system.service.IIcesEquipmentOperationStepService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 设备操作步骤Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-03
 */
@RequiredArgsConstructor
@Service
public class IcesEquipmentOperationStepServiceImpl implements IIcesEquipmentOperationStepService {

    private final IcesEquipmentOperationStepMapper baseMapper;

    /**
     * 查询设备操作步骤
     */
    @Override
    public IcesEquipmentOperationStepVo queryById(Long eosId){
        return baseMapper.selectVoById(eosId);
    }

    /**
     * 查询设备操作步骤列表
     */
    @Override
    public TableDataInfo<IcesEquipmentOperationStepVo> queryPageList(IcesEquipmentOperationStepBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesEquipmentOperationStep> lqw = buildQueryWrapper(bo);
        Page<IcesEquipmentOperationStepVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询设备操作步骤列表
     */
    @Override
    public List<IcesEquipmentOperationStepVo> queryList(IcesEquipmentOperationStepBo bo) {
        LambdaQueryWrapper<IcesEquipmentOperationStep> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesEquipmentOperationStep> buildQueryWrapper(IcesEquipmentOperationStepBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesEquipmentOperationStep> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getEaoId() != null, IcesEquipmentOperationStep::getEaoId, bo.getEaoId());
        lqw.eq(bo.getEoId() != null, IcesEquipmentOperationStep::getEoId, bo.getEoId());
        lqw.eq(bo.getEosDelete() != null, IcesEquipmentOperationStep::getEosDelete, bo.getEosDelete());
        return lqw;
    }

    /**
     * 新增设备操作步骤
     */
    @Override
    public Boolean insertByBo(IcesEquipmentOperationStepBo bo) {
        IcesEquipmentOperationStep add = BeanUtil.toBean(bo, IcesEquipmentOperationStep.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setEosId(add.getEosId());
        }
        return flag;
    }

    /**
     * 修改设备操作步骤
     */
    @Override
    public Boolean updateByBo(IcesEquipmentOperationStepBo bo) {
        IcesEquipmentOperationStep update = BeanUtil.toBean(bo, IcesEquipmentOperationStep.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesEquipmentOperationStep entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除设备操作步骤
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
