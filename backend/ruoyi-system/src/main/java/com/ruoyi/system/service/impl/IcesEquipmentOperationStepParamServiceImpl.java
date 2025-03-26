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
import com.ruoyi.system.domain.bo.IcesEquipmentOperationStepParamBo;
import com.ruoyi.system.domain.vo.IcesEquipmentOperationStepParamVo;
import com.ruoyi.system.domain.IcesEquipmentOperationStepParam;
import com.ruoyi.system.mapper.IcesEquipmentOperationStepParamMapper;
import com.ruoyi.system.service.IIcesEquipmentOperationStepParamService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 设备操作步骤参数Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-03
 */
@RequiredArgsConstructor
@Service
public class IcesEquipmentOperationStepParamServiceImpl implements IIcesEquipmentOperationStepParamService {

    private final IcesEquipmentOperationStepParamMapper baseMapper;
    private final IIcesCodeService codeService;

    /**
     * 查询设备操作步骤参数
     */
    @Override
    public IcesEquipmentOperationStepParamVo queryById(Long eospaId){
        return baseMapper.selectVoById(eospaId);
    }

    /**
     * 查询设备操作步骤参数列表
     */
    @Override
    public TableDataInfo<IcesEquipmentOperationStepParamVo> queryPageList(IcesEquipmentOperationStepParamBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<IcesEquipmentOperationStepParam> lqw = buildQueryWrapper(bo);
        Page<IcesEquipmentOperationStepParamVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询设备操作步骤参数列表
     */
    @Override
    public List<IcesEquipmentOperationStepParamVo> queryList(IcesEquipmentOperationStepParamBo bo) {
        LambdaQueryWrapper<IcesEquipmentOperationStepParam> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<IcesEquipmentOperationStepParam> buildQueryWrapper(IcesEquipmentOperationStepParamBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<IcesEquipmentOperationStepParam> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getEosCode() != null, IcesEquipmentOperationStepParam::getEosCode, bo.getEosCode());
        lqw.eq(bo.getEospaCodeParent() != null, IcesEquipmentOperationStepParam::getEospaCodeParent, bo.getEospaCodeParent());
        lqw.eq(StringUtils.isNotBlank(bo.getEospaType()), IcesEquipmentOperationStepParam::getEospaType, bo.getEospaType());
        lqw.eq(StringUtils.isNotBlank(bo.getEospaPos()), IcesEquipmentOperationStepParam::getEospaPos, bo.getEospaPos());
        lqw.eq(bo.getEospaDelete() != null, IcesEquipmentOperationStepParam::getEospaDelete, bo.getEospaDelete());
        return lqw;
    }

    /**
     * 新增设备操作步骤参数
     */
    @Override
    public Boolean insertByBo(IcesEquipmentOperationStepParamBo bo) {
        bo.setEospaCode(codeService.insertByType("EquipmentOperationStepParam"));
        IcesEquipmentOperationStepParam add = BeanUtil.toBean(bo, IcesEquipmentOperationStepParam.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setEospaId(add.getEospaId());
        }
        return flag;
    }

    /**
     * 修改设备操作步骤参数
     */
    @Override
    public Boolean updateByBo(IcesEquipmentOperationStepParamBo bo) {
        IcesEquipmentOperationStepParam update = BeanUtil.toBean(bo, IcesEquipmentOperationStepParam.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(IcesEquipmentOperationStepParam entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除设备操作步骤参数
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
